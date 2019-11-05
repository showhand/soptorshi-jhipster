import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderDetailComponent } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-purchase-order-detail-extended',
    templateUrl: './commercial-purchase-order-detail-extended.component.html'
})
export class CommercialPurchaseOrderDetailExtendedComponent extends CommercialPurchaseOrderDetailComponent {
    commercialPurchaseOrder: ICommercialPurchaseOrder;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrder }) => {
            this.commercialPurchaseOrder = commercialPurchaseOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
