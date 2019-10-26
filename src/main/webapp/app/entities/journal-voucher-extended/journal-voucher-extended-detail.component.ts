import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherDetailComponent } from 'app/entities/journal-voucher';

@Component({
    selector: 'jhi-journal-voucher-detail',
    templateUrl: './journal-voucher-extended-detail.component.html'
})
export class JournalVoucherExtendedDetailComponent extends JournalVoucherDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
