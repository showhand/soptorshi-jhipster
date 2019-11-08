import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyOrder } from 'app/shared/model/supply-order.model';

@Component({
    selector: 'jhi-supply-order-detail',
    templateUrl: './supply-order-detail.component.html'
})
export class SupplyOrderDetailComponent implements OnInit {
    supplyOrder: ISupplyOrder;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyOrder }) => {
            this.supplyOrder = supplyOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
