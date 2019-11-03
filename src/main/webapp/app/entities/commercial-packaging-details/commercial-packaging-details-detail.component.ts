import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';

@Component({
    selector: 'jhi-commercial-packaging-details-detail',
    templateUrl: './commercial-packaging-details-detail.component.html'
})
export class CommercialPackagingDetailsDetailComponent implements OnInit {
    commercialPackagingDetails: ICommercialPackagingDetails;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackagingDetails }) => {
            this.commercialPackagingDetails = commercialPackagingDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
