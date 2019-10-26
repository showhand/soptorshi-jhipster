import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';

@Component({
    selector: 'jhi-receipt-voucher-detail',
    templateUrl: './receipt-voucher-detail.component.html'
})
export class ReceiptVoucherDetailComponent implements OnInit {
    receiptVoucher: IReceiptVoucher;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ receiptVoucher }) => {
            this.receiptVoucher = receiptVoucher;
        });
    }

    previousState() {
        window.history.back();
    }
}
