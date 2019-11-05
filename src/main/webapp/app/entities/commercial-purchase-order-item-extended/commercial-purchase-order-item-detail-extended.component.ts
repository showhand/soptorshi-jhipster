import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';
import { CommercialPurchaseOrderItemDetailComponent } from 'app/entities/commercial-purchase-order-item';

@Component({
    selector: 'jhi-commercial-purchase-order-item-detail-extended',
    templateUrl: './commercial-purchase-order-item-detail-extended.component.html'
})
export class CommercialPurchaseOrderItemDetailExtendedComponent extends CommercialPurchaseOrderItemDetailComponent {
    commercialPurchaseOrderItem: ICommercialPurchaseOrderItem;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrderItem }) => {
            this.commercialPurchaseOrderItem = commercialPurchaseOrderItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
