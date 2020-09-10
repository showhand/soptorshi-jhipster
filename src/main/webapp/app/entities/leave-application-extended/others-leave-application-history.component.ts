import { Component, OnDestroy, OnInit } from '@angular/core';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { Account, AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { DATE_FORMAT, DAYS, ITEMS_PER_PAGE, MONTHS, YEARS } from 'app/shared';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { IEmployee } from 'app/shared/model/employee.model';
import { ManagerService } from 'app/entities/manager';
import { IManager } from 'app/shared/model/manager.model';
import { IConstantsModel } from 'app/shared/model/constants-model';
import * as moment from 'moment';
import { filter, map } from 'rxjs/operators';
import { LeaveApplicationExtendedService } from 'app/entities/leave-application-extended/leave-application-extended.service';
import { EmployeeExtendedService } from 'app/entities/employee-extended';

@Component({
    selector: 'jhi-others-leave-application-history',
    templateUrl: './others-leave-application-history.component.html'
})
export class OthersLeaveApplicationHistoryComponent implements OnInit, OnDestroy {
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
    currentSearchAsEmployee: IEmployee;
    currentEmployee: IEmployee;
    employees: IEmployee[];
    employeesUnderSupervisor: IManager[];

    days: IConstantsModel[] = DAYS;
    months: IConstantsModel[] = MONTHS;
    years: IConstantsModel[] = YEARS();
    employee: IEmployee;

    fromDate: {
        day: number;
        month: number;
        year: number;
    } = { day: new Date().getDate(), month: new Date().getMonth() + 1, year: new Date().getFullYear() };

    toDate: {
        day: number;
        month: number;
        year: number;
    } = { day: new Date().getDate(), month: new Date().getMonth() + 1, year: new Date().getFullYear() };

    constructor(
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService,
        protected managerService: ManagerService
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
            if (
                this.fromDate.day &&
                this.fromDate.month &&
                this.fromDate.year &&
                this.toDate.day &&
                this.toDate.month &&
                this.toDate.year &&
                this.employee
            ) {
                let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
                let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

                if (from.isBefore(to.add(1))) {
                    this.leaveApplicationService
                        .query({
                            page: this.page,
                            size: this.itemsPerPage,
                            sort: this.sort(),
                            'employeesId.equals': this.employee.id,
                            'fromDate.greaterOrEqualThan': moment(
                                new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`)
                            ).format(DATE_FORMAT),
                            'toDate.lessOrEqualThan': moment(
                                new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`)
                            ).format(DATE_FORMAT)
                        })
                        .subscribe(
                            (ress: HttpResponse<ILeaveApplication[]>) => this.paginateLeaveApplications(ress.body, ress.headers),
                            (ress: HttpErrorResponse) => this.onError(ress.message)
                        );
                } else {
                    this.onError('Invalid dates');
                }
            } else {
                this.onError('Invalid input');
            }
        } else {
            if (
                this.fromDate.day &&
                this.fromDate.month &&
                this.fromDate.year &&
                this.toDate.day &&
                this.toDate.month &&
                this.toDate.year &&
                this.employee
            ) {
                let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
                let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

                if (from.isBefore(to.add(1))) {
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
                                                        'employeesId.equals': this.currentSearchAsEmployee.id,
                                                        'fromDate.greaterOrEqualThan': moment(
                                                            new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`)
                                                        ).format(DATE_FORMAT),
                                                        'toDate.lessOrEqualThan': moment(
                                                            new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`)
                                                        ).format(DATE_FORMAT)
                                                    })
                                                    .subscribe(
                                                        (ress: HttpResponse<ILeaveApplication[]>) =>
                                                            this.paginateLeaveApplications(ress.body, ress.headers),
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
                    this.onError('Invalid dates');
                }
            } else {
                this.onError('Invalid input');
            }
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

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
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

    trackDayId(index: number, item: IConstantsModel) {
        return item.id;
    }

    trackMonthId(index: number, item: IConstantsModel) {
        return item.id;
    }

    trackYearId(index: number, item: IConstantsModel) {
        return item.id;
    }

    generateReport() {
        if (
            this.fromDate.day &&
            this.fromDate.month &&
            this.fromDate.year &&
            this.toDate.day &&
            this.toDate.month &&
            this.toDate.year &&
            this.employee
        ) {
            let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
            let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

            if (from.isBefore(to.add(1))) {
                this.leaveApplicationService.generateReportByFromDateAndToDateAndEmployeeId(from, to, this.employee.employeeId);
            } else {
                this.onError('Invalid dates');
            }
        } else if (
            this.fromDate.day &&
            this.fromDate.month &&
            this.fromDate.year &&
            this.toDate.day &&
            this.toDate.month &&
            this.toDate.year
        ) {
            if (this.accountService.hasAnyAuthority(['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'])) {
                let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
                let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

                if (from.isBefore(to.add(1))) {
                    this.leaveApplicationService.generateReportByFromDateAndToDate(from, to);
                } else {
                    this.onError('Invalid dates');
                }
            } else {
                this.onError('Please fill up all the required information');
            }
        } else {
            this.onError('Invalid input');
        }
    }
}
