import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContraVoucher } from 'app/shared/model/contra-voucher.model';

@Component({
    selector: 'jhi-contra-voucher-detail',
    templateUrl: './contra-voucher-detail.component.html'
})
export class ContraVoucherDetailComponent implements OnInit {
    contraVoucher: IContraVoucher;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contraVoucher }) => {
            this.contraVoucher = contraVoucher;
        });
    }

    previousState() {
        window.history.back();
    }
}
