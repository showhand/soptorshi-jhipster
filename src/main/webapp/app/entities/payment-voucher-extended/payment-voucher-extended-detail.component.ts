import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherDetailComponent } from 'app/entities/payment-voucher';

@Component({
    selector: 'jhi-payment-voucher-detail',
    templateUrl: './payment-voucher-extended-detail.component.html'
})
export class PaymentVoucherExtendedDetailComponent extends PaymentVoucherDetailComponent implements OnInit {
    paymentVoucher: IPaymentVoucher;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
