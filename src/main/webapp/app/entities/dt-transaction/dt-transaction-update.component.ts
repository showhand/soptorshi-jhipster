import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionService } from './dt-transaction.service';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';

@Component({
    selector: 'jhi-dt-transaction-update',
    templateUrl: './dt-transaction-update.component.html'
})
export class DtTransactionUpdateComponent implements OnInit {
    dtTransaction: IDtTransaction;
    isSaving: boolean;

    mstaccounts: IMstAccount[];

    vouchers: IVoucher[];

    currencies: ICurrency[];
    voucherDateDp: any;
    invoiceDateDp: any;
    instrumentDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dtTransactionService: DtTransactionService,
        protected mstAccountService: MstAccountService,
        protected voucherService: VoucherService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dtTransaction }) => {
            this.dtTransaction = dtTransaction;
        });
        this.mstAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstAccount[]>) => response.body)
            )
            .subscribe((res: IMstAccount[]) => (this.mstaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.voucherService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVoucher[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVoucher[]>) => response.body)
            )
            .subscribe((res: IVoucher[]) => (this.vouchers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.currencyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICurrency[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICurrency[]>) => response.body)
            )
            .subscribe((res: ICurrency[]) => (this.currencies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dtTransaction.id !== undefined) {
            this.subscribeToSaveResponse(this.dtTransactionService.update(this.dtTransaction));
        } else {
            this.subscribeToSaveResponse(this.dtTransactionService.create(this.dtTransaction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDtTransaction>>) {
        result.subscribe((res: HttpResponse<IDtTransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMstAccountById(index: number, item: IMstAccount) {
        return item.id;
    }

    trackVoucherById(index: number, item: IVoucher) {
        return item.id;
    }

    trackCurrencyById(index: number, item: ICurrency) {
        return item.id;
    }
}
