import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionDetailComponent } from 'app/entities/requisition';

@Component({
    selector: 'jhi-requisition-extended-detail',
    templateUrl: './requisition-extended-detail.component.html'
})
export class RequisitionExtendedDetailComponent extends RequisitionDetailComponent implements OnInit {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
