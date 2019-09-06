import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMstAccount } from 'app/shared/model/mst-account.model';

@Component({
    selector: 'jhi-mst-account-detail',
    templateUrl: './mst-account-detail.component.html'
})
export class MstAccountDetailComponent implements OnInit {
    mstAccount: IMstAccount;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mstAccount }) => {
            this.mstAccount = mstAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
