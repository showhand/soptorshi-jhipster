import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';

@Component({
    selector: 'jhi-commercial-purchase-order-detail',
    templateUrl: './commercial-purchase-order-detail.component.html'
})
export class CommercialPurchaseOrderDetailComponent implements OnInit {
    commercialPurchaseOrder: ICommercialPurchaseOrder;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrder }) => {
            this.commercialPurchaseOrder = commercialPurchaseOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
