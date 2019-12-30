import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';

@Component({
    selector: 'jhi-requisition-voucher-relation-detail',
    templateUrl: './requisition-voucher-relation-detail.component.html'
})
export class RequisitionVoucherRelationDetailComponent implements OnInit {
    requisitionVoucherRelation: IRequisitionVoucherRelation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisitionVoucherRelation }) => {
            this.requisitionVoucherRelation = requisitionVoucherRelation;
        });
    }

    previousState() {
        window.history.back();
    }
}
