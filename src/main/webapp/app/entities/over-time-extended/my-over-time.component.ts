import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Account, AccountService } from 'app/core';
import { OverTimeExtendedService } from './over-time-extended.service';
import { OverTimeComponent } from 'app/entities/over-time';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IOverTime } from 'app/shared/model/over-time.model';
import * as moment from 'moment';
import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IManager } from 'app/shared/model/manager.model';
import { ManagerService } from 'app/entities/manager';
import { DATE_FORMAT, DAYS, MONTHS, YEARS } from 'app/shared';
import { IConstantsModel } from 'app/shared/model/constants-model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';

@Component({
    selector: 'jhi-my-over-time',
    templateUrl: './my-over-time.component.html'
})
export class MyOverTimeComponent extends OverTimeComponent {
    distinctDates: Moment[];
    currentAccount: Account;
    currentEmployee: IEmployee;
    employeesUnderSupervisor: IManager[];
    employees: IEmployee[];
    employee: IEmployee;
    distinctDate: Moment;

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
        protected overTimeExtendedService: OverTimeExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected managerService: ManagerService,
        protected employeeService: EmployeeExtendedService
    ) {
        super(overTimeExtendedService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => this.addEmployees(res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
        this.registerChangeInOverTimes();
    }

    loadAll() {
        if (this.fromDate.day && this.fromDate.month && this.fromDate.year && this.toDate.day && this.toDate.month && this.toDate.year) {
            let from = moment(new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`));
            let to = moment(new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`));

            if (from.isBefore(to.add(1))) {
                this.overTimeExtendedService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'overTimeDate.greaterOrEqualThan': moment(
                            new Date(`${this.fromDate.month}-${this.fromDate.day}-${this.fromDate.year}`)
                        ).format(DATE_FORMAT),
                        'overTimeDate.lessOrEqualThan': moment(
                            new Date(`${this.toDate.month}-${this.toDate.day}-${this.toDate.year}`)
                        ).format(DATE_FORMAT),
                        'employeeId.equals': this.employees[0].id
                    })
                    .subscribe(
                        (res: HttpResponse<IOverTime[]>) => this.paginateOverTimes(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else {
                this.onError('Invalid dates');
            }
        } else {
            this.onError('Invalid input');
        }
    }

    protected addEmployees(data: IEmployee[]) {
        this.employees = [];
        this.employees = data;
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.overTimes = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
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
                this.overTimeExtendedService.generateReportByFromDateAndToDateAndCurrentLoggedInUser(from, to);
            } else {
                this.onError('Invalid dates');
            }
        } else {
            this.onError('Invalid input');
        }
    }
}
