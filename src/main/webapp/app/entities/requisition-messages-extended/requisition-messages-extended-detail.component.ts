import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesDetailComponent } from 'app/entities/requisition-messages';

@Component({
    selector: 'jhi-requisition-messages-detail',
    templateUrl: './requisition-messages-extended-detail.component.html'
})
export class RequisitionMessagesExtendedDetailComponent extends RequisitionMessagesDetailComponent implements OnInit {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
