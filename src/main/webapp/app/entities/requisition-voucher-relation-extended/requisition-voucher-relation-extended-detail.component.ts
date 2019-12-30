import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationDetailComponent } from 'app/entities/requisition-voucher-relation';

@Component({
    selector: 'jhi-requisition-voucher-relation-detail',
    templateUrl: './requisition-voucher-relation-extended-detail.component.html'
})
export class RequisitionVoucherRelationExtendedDetailComponent extends RequisitionVoucherRelationDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
