import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';

@Component({
    selector: 'jhi-commercial-purchase-order-item-detail',
    templateUrl: './commercial-purchase-order-item-detail.component.html'
})
export class CommercialPurchaseOrderItemDetailComponent implements OnInit {
    commercialPurchaseOrderItem: ICommercialPurchaseOrderItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrderItem }) => {
            this.commercialPurchaseOrderItem = commercialPurchaseOrderItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
