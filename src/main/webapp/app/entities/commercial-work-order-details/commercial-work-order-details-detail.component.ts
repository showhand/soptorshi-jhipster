import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';

@Component({
    selector: 'jhi-commercial-work-order-details-detail',
    templateUrl: './commercial-work-order-details-detail.component.html'
})
export class CommercialWorkOrderDetailsDetailComponent implements OnInit {
    commercialWorkOrderDetails: ICommercialWorkOrderDetails;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrderDetails }) => {
            this.commercialWorkOrderDetails = commercialWorkOrderDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
