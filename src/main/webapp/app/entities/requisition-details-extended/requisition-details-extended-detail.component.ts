import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionDetailsComponent, RequisitionDetailsDetailComponent } from 'app/entities/requisition-details';

@Component({
    selector: 'jhi-requisition-details-extended-detail',
    templateUrl: './requisition-details-extended-detail.component.html'
})
export class RequisitionDetailsExtendedDetailComponent extends RequisitionDetailsDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
