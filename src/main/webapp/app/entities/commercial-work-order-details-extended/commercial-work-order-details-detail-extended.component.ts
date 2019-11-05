import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { CommercialWorkOrderDetailsDetailComponent } from 'app/entities/commercial-work-order-details';

@Component({
    selector: 'jhi-commercial-work-order-details-detail-extended',
    templateUrl: './commercial-work-order-details-detail-extended.component.html'
})
export class CommercialWorkOrderDetailsDetailExtendedComponent extends CommercialWorkOrderDetailsDetailComponent {
    commercialWorkOrderDetails: ICommercialWorkOrderDetails;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrderDetails }) => {
            this.commercialWorkOrderDetails = commercialWorkOrderDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
