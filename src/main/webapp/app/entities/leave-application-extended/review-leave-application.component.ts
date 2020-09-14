import { Component, OnDestroy, OnInit } from '@angular/core';
import { ILeaveApplication, LeaveStatus } from 'app/shared/model/leave-application.model';
import { Account, AccountService } from 'app/core';
import { Observable, Subscription } from 'rxjs';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { DATE_TIME_FORMAT, ITEMS_PER_PAGE } from 'app/shared';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { ManagerService } from 'app/entities/manager';
import { IManager } from 'app/shared/model/manager.model';
import { LeaveAttachmentExtendedService } from 'app/entities/leave-attachment-extended';
import { LeaveAttachment } from 'app/shared/model/leave-attachment.model';
import * as moment from 'moment';

@Component({
    selector: 'jhi-review-leave-application',
    templateUrl: './review-leave-application.component.html'
})
export class ReviewLeaveApplicationComponent implements OnInit, OnDestroy {
    leaveApplications: ILeaveApplication[];
    currentAccount: Account;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
    currentEmployee: IEmployee[];
    manager: IManager[];
    child: IEmployee[];
    leaveAttachments: LeaveAttachment[];

    constructor(
        protected leaveApplicationService: LeaveApplicationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeService,
        protected managerService: ManagerService,
        protected leaveAttachmentExtendedService: LeaveAttachmentExtendedService
    ) {
        this.leaveApplications = [];
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
            this.leaveApplicationService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'status.equals': LeaveStatus.WAITING
                })
                .subscribe(
                    (res: HttpResponse<ILeaveApplication[]>) => {
                        this.paginateLeaveApplications(res.body, res.headers);
                        if (res.body.length > 0) {
                            this.leaveAttachmentExtendedService
                                .query({
                                    'leaveApplicationId.in': res.body.map(value => value.id).join(',')
                                })
                                .subscribe(
                                    (res: HttpResponse<ILeaveApplication[]>) => {
                                        this.leaveAttachments = res.body;
                                    },
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.leaveApplicationService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'employeesId.in': this.child.map(val => val.id).join(','),
                    'status.equals': LeaveStatus.WAITING
                })
                .subscribe(
                    (res: HttpResponse<ILeaveApplication[]>) => {
                        this.paginateLeaveApplications(res.body, res.headers);
                        if (res.body.length > 0) {
                            this.leaveAttachmentExtendedService
                                .query({
                                    'leaveApplicationId.in': res.body.map(value => value.id).join(',')
                                })
                                .subscribe(
                                    (res: HttpResponse<ILeaveApplication[]>) => {
                                        this.leaveAttachments = res.body;
                                    },
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.leaveApplications = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.leaveApplications = [];
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
        this.leaveApplications = [];
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
                this.loadAll();
            } else {
                this.employeeService
                    .query({
                        'employeeId.equals': this.currentAccount.login
                    })
                    .subscribe(
                        (res: HttpResponse<IEmployee[]>) => {
                            this.currentEmployee = res.body;
                            this.managerService
                                .query({
                                    'employeeId.equals': this.currentEmployee[0].id
                                })
                                .subscribe(
                                    (response: HttpResponse<IManager[]>) => {
                                        this.manager = response.body;
                                        const map: string = this.manager.map(val => val.parentEmployeeId).join(',');
                                        this.employeeService
                                            .query({
                                                'id.in': map
                                            })
                                            .subscribe(
                                                (ress: HttpResponse<IEmployee[]>) => {
                                                    this.child = ress.body;
                                                    this.loadAll();
                                                },
                                                (ress: HttpErrorResponse) => this.onError(ress.message)
                                            );
                                    },
                                    (response: HttpErrorResponse) => this.onError(response.message)
                                );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        });
        this.registerChangeInLeaveApplications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILeaveApplication) {
        return item.id;
    }

    registerChangeInLeaveApplications() {
        this.eventSubscriber = this.eventManager.subscribe('leaveApplicationListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateLeaveApplications(data: ILeaveApplication[], headers: HttpHeaders) {
        this.leaveApplications = [];
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.leaveApplications.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    save(val: boolean, leaveApplication: ILeaveApplication) {
        leaveApplication.status = val ? LeaveStatus.ACCEPTED : LeaveStatus.REJECTED;
        leaveApplication.actionTakenByIdId = this.currentEmployee[0].id;
        leaveApplication.actionTakenOn = moment(new Date(), DATE_TIME_FORMAT);
        this.subscribeToSaveResponse(this.leaveApplicationService.update(leaveApplication));
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveApplication>>) {
        result.subscribe((res: HttpResponse<ILeaveApplication>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.loadAll();
    }

    protected onSaveError() {
        this.jhiAlertService.error('Error while saving!! Leave out of balance.');
    }
}
