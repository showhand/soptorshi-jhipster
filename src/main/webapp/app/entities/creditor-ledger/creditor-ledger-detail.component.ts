import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditorLedger } from 'app/shared/model/creditor-ledger.model';

@Component({
    selector: 'jhi-creditor-ledger-detail',
    templateUrl: './creditor-ledger-detail.component.html'
})
export class CreditorLedgerDetailComponent implements OnInit {
    creditorLedger: ICreditorLedger;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditorLedger }) => {
            this.creditorLedger = creditorLedger;
        });
    }

    previousState() {
        window.history.back();
    }
}
