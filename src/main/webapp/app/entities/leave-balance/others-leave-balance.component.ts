import { Component, OnInit } from '@angular/core';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { Account, AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { LeaveBalanceService } from 'app/entities/leave-balance/leave-balance.service';
import { EmployeeService } from 'app/entities/employee';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import * as moment from 'moment';
import { ManagerService } from 'app/entities/manager';
import { IManager } from 'app/shared/model/manager.model';

@Component({
    selector: 'jhi-others-leave-balance',
    templateUrl: './others-leave-balance.component.html',
    styles: []
})
export class OthersLeaveBalanceComponent implements OnInit {
    leaveBalances: ILeaveBalance[];
    employees: IEmployee[];
    currentAccount: Account;
    eventSubscriber: Subscription;
    currentSearch: string;
    account: Account;
    currentEmployee: IEmployee[];

    constructor(
        protected leaveBalanceService: LeaveBalanceService,
        protected employeeService: EmployeeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected managerService: ManagerService
    ) {
        this.leaveBalances = [];
    }

    ngOnInit() {
        this.leaveBalances = [];
        this.accountService.identity().then(account => {
            this.account = account;
            this.employeeService
                .query({
                    'employeeId.equals': this.account.login
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => {
                        this.currentEmployee = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
    }

    search() {
        this.employeeService
            .query({
                'employeeId.equals': this.currentSearch
            })
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => {
                    this.employees = [];
                    this.employees = res.body;
                    this.managerService
                        .query({
                            'parentEmployeeId.equals': this.employees[0].id
                        })
                        .subscribe(
                            (res: HttpResponse<IManager[]>) => {
                                if (res.body[0].employeeId == this.currentEmployee[0].id) {
                                    this.getLeaveBalance(this.employees[0].employeeId);
                                }
                            },
                            (res: HttpErrorResponse) => this.onError(res.message)
                        );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    clear() {
        this.currentSearch = '';
    }

    getLeaveBalance(employeeId: string) {
        this.leaveBalanceService
            .find(employeeId, moment().year())
            .subscribe(
                (res: HttpResponse<ILeaveBalance[]>) => this.constructLeaveBalance(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected constructLeaveBalance(data: ILeaveBalance[], headers: HttpHeaders) {
        this.leaveBalances = [];
        for (let i = 0; i < data.length; i++) {
            this.leaveBalances.push(data[i]);
        }
    }

    trackId(index: number, item: ILeaveApplication) {
        return item.id;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
