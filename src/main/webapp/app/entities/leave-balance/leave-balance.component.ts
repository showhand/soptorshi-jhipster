import { Component, OnInit } from '@angular/core';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { Account, AccountService } from 'app/core';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { Subscription } from 'rxjs';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { LeaveBalanceService } from 'app/entities/leave-balance/leave-balance.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import moment = require('moment');

@Component({
    selector: 'jhi-leave-balance',
    templateUrl: './leave-balance.component.html',
    styles: []
})
export class LeaveBalanceComponent implements OnInit {
    leaveBalances: ILeaveBalance[];
    employee: IEmployee;
    currentAccount: Account;
    eventSubscriber: Subscription;

    constructor(
        protected leaveBalanceService: LeaveBalanceService,
        protected employeeService: EmployeeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.leaveBalances = [];
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => {
                        this.employee = res.body[0];
                        this.getLeaveBalance(this.employee.employeeId);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
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
