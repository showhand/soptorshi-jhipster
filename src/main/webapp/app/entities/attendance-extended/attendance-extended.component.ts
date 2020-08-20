import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IAttendance } from 'app/shared/model/attendance.model';
import { AccountService } from 'app/core';

import { DATE_FORMAT, DAYS, MONTHS, YEARS } from 'app/shared';
import { AttendanceExtendedService } from './attendance-extended.service';
import * as moment from 'moment';
import { AttendanceComponent } from 'app/entities/attendance';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { IConstantsModel } from 'app/shared/model/constants-model';

@Component({
    selector: 'jhi-attendance-extended',
    templateUrl: './attendance-extended.component.html'
})
export class AttendanceExtendedComponent extends AttendanceComponent implements OnInit {
    attendances: IAttendance[];
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
        protected attendanceServiceExtended: AttendanceExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService
    ) {
        super(attendanceServiceExtended, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
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
                this.attendanceServiceExtended
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'attendanceDate.greaterOrEqualThan': from.format(DATE_FORMAT),
                        'attendanceDate.lessOrEqualThan': to.format(DATE_FORMAT),
                        'employeeId.equals': this.employee.id
                    })
                    .subscribe(
                        (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
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
            let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
            let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

            if (from.isBefore(to.add(1))) {
                this.attendanceServiceExtended
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'attendanceDate.greaterOrEqualThan': moment(
                            new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`)
                        ).format(DATE_FORMAT),
                        'attendanceDate.lessOrEqualThan': moment(
                            new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`)
                        ).format(DATE_FORMAT)
                    })
                    .subscribe(
                        (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else {
                this.onError('Invalid dates');
            }
        } else if (this.employee) {
            this.attendanceServiceExtended
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'employeeId.equals': this.employee.id
                })
                .subscribe(
                    (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.onError('Invalid input');
        }
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.attendances = [];
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
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.employeeService
            .query()
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => this.addEmployees(res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.registerChangeInAttendances();
    }

    protected addEmployees(data: IEmployee[]) {
        this.employees = [];
        this.employees = data;
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
                this.attendanceServiceExtended.generateReportByFromDateAndToDateAndEmployeeId(from, to, this.employee.employeeId);
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
            let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
            let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

            if (from.isBefore(to.add(1))) {
                this.attendanceServiceExtended.generateReportByFromDateAndToDate(from, to);
            } else {
                this.onError('Invalid dates');
            }
        } else if (this.employee) {
            this.attendanceServiceExtended.generateReportByEmployeeId(this.employee.employeeId);
        } else {
            this.onError('Invalid input');
        }
    }
}
