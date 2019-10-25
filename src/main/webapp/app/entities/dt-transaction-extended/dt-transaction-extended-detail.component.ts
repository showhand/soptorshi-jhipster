import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionDetailComponent } from 'app/entities/dt-transaction';

@Component({
    selector: 'jhi-dt-transaction-detail',
    templateUrl: './dt-transaction-extended-detail.component.html'
})
export class DtTransactionExtendedDetailComponent extends DtTransactionDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
