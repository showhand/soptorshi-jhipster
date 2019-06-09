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
    departments: IDepartment[];
    departmentMap: any;
    designations: IDesignation[];
    designationMap: any;
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
        protected userService: UserService
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
        if (this.currentSearch) {
            this.employeeService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => this.paginateEmployees(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        if (this.accountService.hasAnyAuthority(['ROLE_ADMIN'])) {
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
        } else {
            this.employeeService
                .query({
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'manager.equals': this.authenticatedEmployee.id
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => this.paginateEmployees(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }

        this.departmentService
            .query({
                page: 0,
                size: 1000,
                sort: this.sort()
            })
            .subscribe(
                (response: HttpResponse<IDepartment[]>) => {
                    this.departments = response.body;
                    this.departmentMap = {};
                    this.departments.forEach((d: IDepartment) => (this.departmentMap[d.id] = d));
                },
                (errorResponse: HttpErrorResponse) => {
                    this.jhiAlertService.error('Error in fetching department data');
                }
            );

        this.designationService
            .query({
                page: 0,
                size: 1000,
                sort: this.sort()
            })
            .subscribe(
                (response: HttpResponse<IDesignation[]>) => {
                    this.designations = response.body;
                    this.designationMap = {};
                    this.designations.forEach((d: IDesignation) => (this.designationMap[d.id] = d));
                },
                (errorResponse: HttpErrorResponse) => {
                    this.jhiAlertService.error('Error in fetching designation data');
                }
            );
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
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
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
        console.log('Employee data');
        console.log(data);
        if (this.accountService.hasAnyAuthority(['ROLE_ADMIN', 'ROLE_HR_ADMIN_EXECUTIVE'])) {
            this.employees = data;
        } else {
            this.employees = [];
            console.log('IN here');
            for (let i = 0; i < data.length; i++) {
                if ((data[i].manager = this.authenticatedEmployee.id)) {
                    console.log('Employess to be pushed');
                    console.log(data[i]);
                    this.employees.push(data[i]);
                }
            }
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
