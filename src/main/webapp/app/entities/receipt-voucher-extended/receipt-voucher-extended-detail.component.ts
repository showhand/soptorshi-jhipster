import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { ReceiptVoucherDetailComponent } from 'app/entities/receipt-voucher';

@Component({
    selector: 'jhi-receipt-voucher-detail',
    templateUrl: './receipt-voucher-extended-detail.component.html'
})
export class ReceiptVoucherExtendedDetailComponent extends ReceiptVoucherDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
