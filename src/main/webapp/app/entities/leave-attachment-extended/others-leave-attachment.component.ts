import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { LeaveAttachmentExtendedService } from './leave-attachment-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { IManager } from 'app/shared/model/manager.model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { ManagerService } from 'app/entities/manager';
import { filter, map } from 'rxjs/operators';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationExtendedService } from 'app/entities/leave-application-extended';

@Component({
    selector: 'jhi-others-leave-attachment',
    templateUrl: './others-leave-attachment.component.html'
})
export class OthersLeaveAttachmentComponent implements OnInit, OnDestroy {
    leaveAttachments: ILeaveAttachment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;

    employees: IEmployee[];
    employee: IEmployee;
    currentEmployee: IEmployee;
    employeesUnderSupervisor: IManager[];

    currentSearchAsEmployee: IEmployee;

    leaveApplications: ILeaveApplication[];

    constructor(
        protected leaveAttachmentService: LeaveAttachmentExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService,
        protected managerService: ManagerService,
        protected leaveApplicationService: LeaveApplicationExtendedService
    ) {
        this.leaveAttachments = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.accountService.hasAnyAuthority(['ROLE_ADMIN']) || this.accountService.hasAnyAuthority(['ROLE_LEAVE_ADMIN'])) {
            if (this.employee) {
                this.leaveApplicationService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'employeesId.equals': this.employee.id
                    })
                    .subscribe(
                        (ress: HttpResponse<ILeaveApplication[]>) => {
                            this.paginateLeaveApplications(ress.body, ress.headers);
                            this.leaveAttachmentService
                                .query({
                                    page: this.page,
                                    size: this.itemsPerPage,
                                    sort: this.sort(),
                                    'leaveApplicationId.in': ress.body.map(value => value.id).join(',')
                                })
                                .subscribe(
                                    (res: HttpResponse<ILeaveAttachment[]>) => this.paginateLeaveAttachments(res.body, res.headers),
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        },
                        (ress: HttpErrorResponse) => this.onError(ress.message)
                    );
            } else {
                this.onError('Please select an employee first');
            }
        } else {
            if (this.employee) {
                this.employeeService
                    .query({
                        'employeeId.equals': this.employee.employeeId
                    })
                    .subscribe(
                        (res: HttpResponse<IEmployee[]>) => {
                            this.currentSearchAsEmployee = res.body[0];
                            this.managerService
                                .query({
                                    'parentEmployeeId.equals': res.body[0].id
                                })
                                .subscribe(
                                    (response: HttpResponse<IManager[]>) => {
                                        if (response.body[0].employeeId === this.currentEmployee.id) {
                                            this.leaveApplicationService
                                                .query({
                                                    page: this.page,
                                                    size: this.itemsPerPage,
                                                    sort: this.sort(),
                                                    'employeesId.equals': this.currentSearchAsEmployee.id
                                                })
                                                .subscribe(
                                                    (ress: HttpResponse<ILeaveApplication[]>) => {
                                                        this.paginateLeaveApplications(ress.body, ress.headers);
                                                        this.leaveAttachmentService
                                                            .query({
                                                                page: this.page,
                                                                size: this.itemsPerPage,
                                                                sort: this.sort(),
                                                                'leaveApplicationId.in': ress.body.map(value => value.id).join(',')
                                                            })
                                                            .subscribe(
                                                                (res: HttpResponse<ILeaveAttachment[]>) =>
                                                                    this.paginateLeaveAttachments(res.body, res.headers),
                                                                (res: HttpErrorResponse) => this.onError(res.message)
                                                            );
                                                    },
                                                    (ress: HttpErrorResponse) => this.onError(ress.message)
                                                );
                                        }
                                    },
                                    (response: HttpErrorResponse) => this.onError(response.message)
                                );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else {
                this.onError('Please select an employee first');
            }
        }
    }

    reset() {
        this.page = 0;
        this.leaveAttachments = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.leaveAttachments = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.leaveAttachments = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            if (this.accountService.hasAnyAuthority(['ROLE_ADMIN']) || this.accountService.hasAnyAuthority(['ROLE_LEAVE_ADMIN'])) {
                this.employeeService
                    .query()
                    .pipe(
                        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                        map((response: HttpResponse<IEmployee[]>) => response.body)
                    )
                    .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
            } else {
                this.employeeService
                    .query({
                        'employeeId.equals': this.currentAccount.login
                    })
                    .subscribe(
                        (res: HttpResponse<IEmployee[]>) => {
                            this.currentEmployee = res.body[0];
                            this.managerService
                                .query({
                                    'employeeId.equals': this.currentEmployee.id
                                })
                                .subscribe(
                                    (res: HttpResponse<IManager[]>) => {
                                        this.employeesUnderSupervisor = res.body;
                                        const map: string = this.employeesUnderSupervisor.map(val => val.parentEmployeeId).join(',');
                                        this.employeeService
                                            .query({
                                                'id.in': [map]
                                            })
                                            .subscribe(
                                                (res: HttpResponse<IEmployee[]>) => (this.employees = res.body),
                                                (res: HttpErrorResponse) => this.onError(res.message)
                                            );
                                    },
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        });
        this.registerChangeInLeaveAttachments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILeaveAttachment) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInLeaveAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('leaveAttachmentListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateLeaveAttachments(data: ILeaveAttachment[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.leaveAttachments.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    protected paginateLeaveApplications(data: ILeaveApplication[], headers: HttpHeaders) {
        this.leaveApplications = [];
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.leaveApplications.push(data[i]);
        }
    }
}
