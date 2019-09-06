import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDebtorLedger } from 'app/shared/model/debtor-ledger.model';

@Component({
    selector: 'jhi-debtor-ledger-detail',
    templateUrl: './debtor-ledger-detail.component.html'
})
export class DebtorLedgerDetailComponent implements OnInit {
    debtorLedger: IDebtorLedger;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ debtorLedger }) => {
            this.debtorLedger = debtorLedger;
        });
    }

    previousState() {
        window.history.back();
    }
}
