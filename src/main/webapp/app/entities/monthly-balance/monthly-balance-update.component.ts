import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMonthlyBalance } from 'app/shared/model/monthly-balance.model';
import { MonthlyBalanceService } from './monthly-balance.service';
import { IAccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from 'app/entities/account-balance';

@Component({
    selector: 'jhi-monthly-balance-update',
    templateUrl: './monthly-balance-update.component.html'
})
export class MonthlyBalanceUpdateComponent implements OnInit {
    monthlyBalance: IMonthlyBalance;
    isSaving: boolean;

    accountbalances: IAccountBalance[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected monthlyBalanceService: MonthlyBalanceService,
        protected accountBalanceService: AccountBalanceService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ monthlyBalance }) => {
            this.monthlyBalance = monthlyBalance;
        });
        this.accountBalanceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAccountBalance[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAccountBalance[]>) => response.body)
            )
            .subscribe((res: IAccountBalance[]) => (this.accountbalances = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.monthlyBalance.id !== undefined) {
            this.subscribeToSaveResponse(this.monthlyBalanceService.update(this.monthlyBalance));
        } else {
            this.subscribeToSaveResponse(this.monthlyBalanceService.create(this.monthlyBalance));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlyBalance>>) {
        result.subscribe((res: HttpResponse<IMonthlyBalance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAccountBalanceById(index: number, item: IAccountBalance) {
        return item.id;
    }
}
