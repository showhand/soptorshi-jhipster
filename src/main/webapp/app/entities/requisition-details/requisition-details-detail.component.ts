import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';

@Component({
    selector: 'jhi-requisition-details-detail',
    templateUrl: './requisition-details-detail.component.html'
})
export class RequisitionDetailsDetailComponent implements OnInit {
    requisitionDetails: IRequisitionDetails;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisitionDetails }) => {
            this.requisitionDetails = requisitionDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
