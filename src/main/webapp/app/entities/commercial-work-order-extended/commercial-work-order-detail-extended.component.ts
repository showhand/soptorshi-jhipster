import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderDetailComponent } from 'app/entities/commercial-work-order';

@Component({
    selector: 'jhi-commercial-work-order-detail-extended',
    templateUrl: './commercial-work-order-detail-extended.component.html'
})
export class CommercialWorkOrderDetailExtendedComponent extends CommercialWorkOrderDetailComponent {
    commercialWorkOrder: ICommercialWorkOrder;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrder }) => {
            this.commercialWorkOrder = commercialWorkOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
