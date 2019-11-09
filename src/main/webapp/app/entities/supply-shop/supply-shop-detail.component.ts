import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyShop } from 'app/shared/model/supply-shop.model';

@Component({
    selector: 'jhi-supply-shop-detail',
    templateUrl: './supply-shop-detail.component.html'
})
export class SupplyShopDetailComponent implements OnInit {
    supplyShop: ISupplyShop;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyShop }) => {
            this.supplyShop = supplyShop;
        });
    }

    previousState() {
        window.history.back();
    }
}
