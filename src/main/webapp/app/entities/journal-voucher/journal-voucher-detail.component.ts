import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';

@Component({
    selector: 'jhi-journal-voucher-detail',
    templateUrl: './journal-voucher-detail.component.html'
})
export class JournalVoucherDetailComponent implements OnInit {
    journalVoucher: IJournalVoucher;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalVoucher }) => {
            this.journalVoucher = journalVoucher;
        });
    }

    previousState() {
        window.history.back();
    }
}
