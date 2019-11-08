import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';

@Component({
    selector: 'jhi-supply-money-collection-detail',
    templateUrl: './supply-money-collection-detail.component.html'
})
export class SupplyMoneyCollectionDetailComponent implements OnInit {
    supplyMoneyCollection: ISupplyMoneyCollection;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyMoneyCollection }) => {
            this.supplyMoneyCollection = supplyMoneyCollection;
        });
    }

    previousState() {
        window.history.back();
    }
}
