import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { AccountService, UserService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { EmployeeService } from './employee.service';
import { DepartmentService } from 'app/entities/department';
import { Designation, IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation';
import { IDepartment } from 'app/shared/model/department.model';
import { ManagerService } from 'app/entities/manager';
import { IManager } from 'app/shared/model/manager.model';

@Component({
    selector: 'jhi-employee',
    templateUrl: './employee.component.html'
})
export class EmployeeComponent implements OnInit, OnDestroy {
    currentAccount: any;
    employees: IEmployee[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    authenticatedEmployee: IEmployee;

    constructor(
        protected employeeService: EmployeeService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected departmentService: DepartmentService,
        protected designationService: DesignationService,
        protected userService: UserService,
        protected managerService: ManagerService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.accountService.hasAnyAuthority(['ROLE_ADMIN', 'ROLE_HR_ADMIN_EXECUTIVE'])) {
            if (this.currentSearch) {
                this.employeeService
                    .query({
                        page: this.page - 1,
                        'fullName.contains': this.currentSearch,
                        size: this.itemsPerPage,
                        sort: this.sort()
                    })
                    .subscribe(
                        (res: HttpResponse<IEmployee[]>) => this.paginateEmployees(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
                return;
            }
            this.employeeService
                .query({
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => this.paginateEmployees(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.accountService.hasAnyAuthority(['ROLE_EMPLOYEE_MANAGEMENT'])) {
            this.managerService
                .query({
                    'employeeId.equals': this.authenticatedEmployee.id
                })
                .subscribe(
                    (res: HttpResponse<IManager[]>) => {
                        const managerId: number[] = [];
                        res.body.forEach(m => {
                            managerId.push(m.parentEmployeeId);
                        });
                        if (this.currentSearch) {
                            this.employeeService
                                .query({
                                    page: this.page - 1,
                                    'fullName.contains': this.currentSearch,
                                    'id.in': managerId,
                                    size: this.itemsPerPage,
                                    sort: this.sort()
                                })
                                .subscribe(
                                    (response: HttpResponse<IEmployee[]>) => this.paginateEmployees(response.body, response.headers),
                                    (response: HttpErrorResponse) => this.onError(response.message)
                                );
                            return;
                        }
                        this.employeeService
                            .query({
                                page: this.page - 1,
                                size: this.itemsPerPage,
                                'id.in': managerId,
                                sort: this.sort()
                            })
                            .subscribe(
                                (response: HttpResponse<IEmployee[]>) => this.paginateEmployees(response.body, response.headers),
                                (response: HttpErrorResponse) => this.onError(response.message)
                            );
                    },
                    (error: HttpErrorResponse) => this.onError(error.message)
                );
        }
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/employee'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/employee',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/employee',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            console.log('current account');
            console.log(account);
            this.employeeService
                .query({
                    'employeeId.equals': account.login
                })
                .subscribe((res: HttpResponse<IEmployee[]>) => {
                    this.authenticatedEmployee = res.body[0];
                    this.loadAll();
                });
        });
        this.registerChangeInEmployees();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmployee) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInEmployees() {
        this.eventSubscriber = this.eventManager.subscribe('employeeListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateEmployees(data: IEmployee[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.employees = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
