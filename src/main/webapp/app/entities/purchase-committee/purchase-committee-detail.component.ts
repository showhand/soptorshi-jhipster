import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseCommittee } from 'app/shared/model/purchase-committee.model';

@Component({
    selector: 'jhi-purchase-committee-detail',
    templateUrl: './purchase-committee-detail.component.html'
})
export class PurchaseCommitteeDetailComponent implements OnInit {
    purchaseCommittee: IPurchaseCommittee;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseCommittee }) => {
            this.purchaseCommittee = purchaseCommittee;
        });
    }

    previousState() {
        window.history.back();
    }
}
