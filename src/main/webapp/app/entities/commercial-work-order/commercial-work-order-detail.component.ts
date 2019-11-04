import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';

@Component({
    selector: 'jhi-commercial-work-order-detail',
    templateUrl: './commercial-work-order-detail.component.html'
})
export class CommercialWorkOrderDetailComponent implements OnInit {
    commercialWorkOrder: ICommercialWorkOrder;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrder }) => {
            this.commercialWorkOrder = commercialWorkOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
