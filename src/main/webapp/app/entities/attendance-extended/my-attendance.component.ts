import { Component, OnInit } from '@angular/core';
import { IAttendance } from 'app/shared/model/attendance.model';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { Account, AccountService } from 'app/core';
import { DATE_FORMAT, DAYS, ITEMS_PER_PAGE, MONTHS, YEARS } from 'app/shared';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import * as moment from 'moment';
import { AttendanceExtendedService } from 'app/entities/attendance-extended/attendance-extended.service';
import { EmployeeService } from 'app/entities/employee';
import { IEmployee } from 'app/shared/model/employee.model';
import { IConstantsModel } from 'app/shared/model/constants-model';

@Component({
    selector: 'jhi-my-attendance',
    templateUrl: './my-attendance.component.html',
    styles: []
})
export class MyAttendanceComponent implements OnInit {
    attendances: IAttendance[];
    currentAccount: Account;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
    currentEmployee: IEmployee;

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
        protected attendanceExtendedService: AttendanceExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeService
    ) {
        this.attendances = [];
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
                            this.currentEmployee = res.body[0];
                            this.attendanceExtendedService
                                .query({
                                    page: this.page,
                                    size: this.itemsPerPage,
                                    sort: this.sort(),
                                    'attendanceDate.greaterOrEqualThan': from.format(DATE_FORMAT),
                                    'attendanceDate.lessOrEqualThan': to.format(DATE_FORMAT),
                                    'employeeId.equals': this.currentEmployee.id
                                })
                                .subscribe(
                                    (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else {
                this.onError('Invalid dates');
            }
        } else {
            this.onError('Invalid dates');
        }
    }

    reset() {
        this.page = 0;
        this.attendances = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.attendances = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = moment(new Date())
            .add(-1, 'days')
            .toString();
        this.loadAll();
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
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAttendances();
    }

    trackId(index: number, item: IAttendance) {
        return item.id;
    }

    registerChangeInAttendances() {
        this.eventSubscriber = this.eventManager.subscribe('attendanceListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateAttendances(data: IAttendance[], headers: HttpHeaders) {
        this.attendances = [];
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.attendances.push(data[i]);
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
        if (this.fromDate.day && this.fromDate.month && this.fromDate.year && this.toDate.day && this.toDate.month && this.toDate.year) {
            let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
            let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

            if (from.isBefore(to.add(1))) {
                this.attendanceExtendedService.generateReportByFromDateAndToDateAndCurrentLoggedInUser(from, to);
            } else {
                this.onError('Invalid dates');
            }
        } else {
            this.onError('Invalid input');
        }
    }
}
