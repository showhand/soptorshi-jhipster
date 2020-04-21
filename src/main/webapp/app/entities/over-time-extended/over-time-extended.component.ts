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
import { EmployeeService } from 'app/entities/employee';
import { DATE_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-over-time-extended',
    templateUrl: './over-time-extended.component.html'
})
export class OverTimeExtendedComponent extends OverTimeComponent {
    distinctDates: Moment[];
    currentAccount: Account;
    currentEmployee: IEmployee;
    employeesUnderSupervisor: IManager[];
    employees: IEmployee[];
    employee: IEmployee;
    distinctDate: Moment;

    constructor(
        protected overTimeService: OverTimeExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected managerService: ManagerService,
        protected employeeService: EmployeeService
    ) {
        super(overTimeService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOverTimes();

        this.overTimeService
            .getDistinctOverTimeDate()
            .subscribe(
                (res: HttpResponse<Moment[]>) => this.addDistinctOverTimeDates(res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.accountService.identity().then(account => {
            this.currentAccount = account;
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
        });
    }

    protected addDistinctOverTimeDates(data: Moment[]) {
        this.distinctDates = data;
    }

    loadAll() {
        if (this.currentSearch && this.employee) {
            this.overTimeService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'employeeId.equals': this.employee.id,
                    'overTimeDate.equals': moment(this.currentSearch).format(DATE_FORMAT)
                })
                .subscribe(
                    (res: HttpResponse<IOverTime[]>) => this.paginateOverTimes(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.currentSearch) {
            this.overTimeService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'overTimeDate.equals': moment(this.currentSearch).format(DATE_FORMAT)
                })
                .subscribe(
                    (res: HttpResponse<IOverTime[]>) => this.paginateOverTimes(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.employee) {
            this.overTimeService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'employeeId.equals': this.employee.id
                })
                .subscribe(
                    (res: HttpResponse<IOverTime[]>) => this.paginateOverTimes(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
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
}
