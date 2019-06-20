import { Component, OnInit } from '@angular/core';
import { LeaveBalanceService } from 'app/entities/leave-application/leave-balance.service';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { Account, AccountService } from 'app/core';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { Subscription } from 'rxjs';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-leave-balance',
    templateUrl: './leave-balance.component.html',
    styles: []
})
export class LeaveBalanceComponent implements OnInit {
    leaveBalances: ILeaveBalance[];
    currentAccount: Account;
    eventSubscriber: Subscription;

    constructor(
        protected leaveBalanceService: LeaveBalanceService,
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
            this.getLeaveBalance(this.currentAccount.login);
        });
    }

    getLeaveBalance(id: string) {
        this.leaveBalanceService
            .find(id)
            .subscribe(
                (res: HttpResponse<ILeaveBalance[]>) => this.constructLeaveBalance(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected constructLeaveBalance(data: ILeaveBalance[], headers: HttpHeaders) {
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
