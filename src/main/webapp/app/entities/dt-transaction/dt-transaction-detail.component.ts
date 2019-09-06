import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDtTransaction } from 'app/shared/model/dt-transaction.model';

@Component({
    selector: 'jhi-dt-transaction-detail',
    templateUrl: './dt-transaction-detail.component.html'
})
export class DtTransactionDetailComponent implements OnInit {
    dtTransaction: IDtTransaction;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dtTransaction }) => {
            this.dtTransaction = dtTransaction;
        });
    }

    previousState() {
        window.history.back();
    }
}
