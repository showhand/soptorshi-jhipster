import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherService } from './payment-voucher.service';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';

@Component({
    selector: 'jhi-payment-voucher-update',
    templateUrl: './payment-voucher-update.component.html'
})
export class PaymentVoucherUpdateComponent implements OnInit {
    paymentVoucher: IPaymentVoucher;
    isSaving: boolean;

    mstaccounts: IMstAccount[];
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected paymentVoucherService: PaymentVoucherService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentVoucher }) => {
            this.paymentVoucher = paymentVoucher;
        });
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
        if (this.paymentVoucher.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentVoucherService.update(this.paymentVoucher));
        } else {
            this.subscribeToSaveResponse(this.paymentVoucherService.create(this.paymentVoucher));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentVoucher>>) {
        result.subscribe(
            (res: HttpResponse<IPaymentVoucher>) => {
                this.paymentVoucher = res.body;
                this.onSaveSuccess();
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
}
