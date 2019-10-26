import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';

@Component({
    selector: 'jhi-payment-voucher-detail',
    templateUrl: './payment-voucher-detail.component.html'
})
export class PaymentVoucherDetailComponent implements OnInit {
    paymentVoucher: IPaymentVoucher;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paymentVoucher }) => {
            this.paymentVoucher = paymentVoucher;
        });
    }

    previousState() {
        window.history.back();
    }
}
