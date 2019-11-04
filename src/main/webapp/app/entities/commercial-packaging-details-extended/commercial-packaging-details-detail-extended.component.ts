import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { CommercialPackagingDetailsDetailComponent } from 'app/entities/commercial-packaging-details';

@Component({
    selector: 'jhi-commercial-packaging-details-detail-extended',
    templateUrl: './commercial-packaging-details-detail-extended.component.html'
})
export class CommercialPackagingDetailsDetailExtendedComponent extends CommercialPackagingDetailsDetailComponent {
    commercialPackagingDetails: ICommercialPackagingDetails;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackagingDetails }) => {
            this.commercialPackagingDetails = commercialPackagingDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
