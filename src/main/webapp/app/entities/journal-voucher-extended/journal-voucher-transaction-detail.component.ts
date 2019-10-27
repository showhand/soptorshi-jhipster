import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionDetailComponent } from 'app/entities/dt-transaction';

@Component({
    selector: 'jhi-dt-transaction-detail',
    templateUrl: './journal-voucher-transaction-detail.component.html'
})
export class JournalVoucherTransactionDetailComponent extends DtTransactionDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
