import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ContraVoucherService } from './contra-voucher.service';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';

@Component({
    selector: 'jhi-contra-voucher-update',
    templateUrl: './contra-voucher-update.component.html'
})
export class ContraVoucherUpdateComponent implements OnInit {
    contraVoucher: IContraVoucher;
    isSaving: boolean;

    currencies: ICurrency[];
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected contraVoucherService: ContraVoucherService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contraVoucher }) => {
            this.contraVoucher = contraVoucher;
        });
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
        if (this.contraVoucher.id !== undefined) {
            this.subscribeToSaveResponse(this.contraVoucherService.update(this.contraVoucher));
        } else {
            this.subscribeToSaveResponse(this.contraVoucherService.create(this.contraVoucher));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContraVoucher>>) {
        result.subscribe((res: HttpResponse<IContraVoucher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCurrencyById(index: number, item: ICurrency) {
        return item.id;
    }
}
