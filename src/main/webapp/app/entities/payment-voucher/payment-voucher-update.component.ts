import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherService } from './payment-voucher.service';

@Component({
    selector: 'jhi-payment-voucher-update',
    templateUrl: './payment-voucher-update.component.html'
})
export class PaymentVoucherUpdateComponent implements OnInit {
    paymentVoucher: IPaymentVoucher;
    isSaving: boolean;
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(protected paymentVoucherService: PaymentVoucherService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentVoucher }) => {
            this.paymentVoucher = paymentVoucher;
        });
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
        result.subscribe((res: HttpResponse<IPaymentVoucher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
