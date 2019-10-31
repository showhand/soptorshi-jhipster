import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherUpdateComponent } from 'app/entities/payment-voucher';
import { PaymentVoucherExtendedService } from 'app/entities/payment-voucher-extended/payment-voucher-extended.service';

@Component({
    selector: 'jhi-payment-voucher-update',
    templateUrl: './payment-voucher-extended-update.component.html'
})
export class PaymentVoucherExtendedUpdateComponent extends PaymentVoucherUpdateComponent implements OnInit {
    paymentVoucher: IPaymentVoucher;
    isSaving: boolean;
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(protected paymentVoucherService: PaymentVoucherExtendedService, protected activatedRoute: ActivatedRoute) {
        super(paymentVoucherService, activatedRoute);
    }
}
