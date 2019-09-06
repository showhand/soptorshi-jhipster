import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from './account-balance.service';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';

@Component({
    selector: 'jhi-account-balance-update',
    templateUrl: './account-balance-update.component.html'
})
export class AccountBalanceUpdateComponent implements OnInit {
    accountBalance: IAccountBalance;
    isSaving: boolean;

    financialaccountyears: IFinancialAccountYear[];

    mstaccounts: IMstAccount[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected accountBalanceService: AccountBalanceService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ accountBalance }) => {
            this.accountBalance = accountBalance;
        });
        this.financialAccountYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFinancialAccountYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFinancialAccountYear[]>) => response.body)
            )
            .subscribe(
                (res: IFinancialAccountYear[]) => (this.financialaccountyears = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.mstAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstAccount[]>) => response.body)
            )
            .subscribe((res: IMstAccount[]) => (this.mstaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.accountBalance.id !== undefined) {
            this.subscribeToSaveResponse(this.accountBalanceService.update(this.accountBalance));
        } else {
            this.subscribeToSaveResponse(this.accountBalanceService.create(this.accountBalance));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountBalance>>) {
        result.subscribe((res: HttpResponse<IAccountBalance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFinancialAccountYearById(index: number, item: IFinancialAccountYear) {
        return item.id;
    }

    trackMstAccountById(index: number, item: IMstAccount) {
        return item.id;
    }
}
