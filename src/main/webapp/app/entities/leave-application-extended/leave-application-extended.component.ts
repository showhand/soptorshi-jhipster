import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveApplicationComponent } from 'app/entities/leave-application';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { EmployeeService } from 'app/entities/employee';
import { IEmployee } from 'app/shared/model/employee.model';
import { IConstantsModel } from 'app/shared/model/constants-model';
import { DATE_FORMAT, DAYS, MONTHS, YEARS } from 'app/shared';
import * as moment from 'moment';

@Component({
    selector: 'jhi-leave-application-extended',
    templateUrl: './leave-application-extended.component.html'
})
export class LeaveApplicationExtendedComponent extends LeaveApplicationComponent implements OnInit {
    employee: IEmployee;

    days: IConstantsModel[] = DAYS;
    months: IConstantsModel[] = MONTHS;
    years: IConstantsModel[] = YEARS();

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
        protected employeeService: EmployeeService
    ) {
        super(leaveApplicationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.loadAll();
        });
        this.registerChangeInLeaveApplications();
    }

    loadAll() {
        if (this.fromDate.day && this.fromDate.month && this.fromDate.year && this.toDate.day && this.toDate.month && this.toDate.year) {
            let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
            let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

            if (from.isBefore(to.add(1))) {
                this.employeeService
                    .query({
                        'employeeId.equals': this.currentAccount.login
                    })
                    .subscribe(
                        (res: HttpResponse<IEmployee[]>) => {
                            this.employee = res.body[0];
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
                                    (res: HttpResponse<ILeaveApplication[]>) => this.paginateLeaveApplications(res.body, res.headers),
                                    (res: HttpErrorResponse) => this.onError(res.message)
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
        if (this.fromDate.day && this.fromDate.month && this.fromDate.year && this.toDate.day && this.toDate.month && this.toDate.year) {
            let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
            let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

            if (from.isBefore(to.add(1))) {
                this.leaveApplicationService.generateReportByFromDateAndToDateAndEmployeeId(from, to, this.employee.employeeId);
            } else {
                this.onError('Invalid dates');
            }
        } else {
            this.onError('Invalid input');
        }
    }
}
