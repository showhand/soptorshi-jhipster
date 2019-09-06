import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';

@Component({
    selector: 'jhi-voucher-number-control-detail',
    templateUrl: './voucher-number-control-detail.component.html'
})
export class VoucherNumberControlDetailComponent implements OnInit {
    voucherNumberControl: IVoucherNumberControl;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ voucherNumberControl }) => {
            this.voucherNumberControl = voucherNumberControl;
        });
    }

    previousState() {
        window.history.back();
    }
}
