import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationDetailComponent } from 'app/entities/purchase-order-voucher-relation';

@Component({
    selector: 'jhi-purchase-order-voucher-relation-detail',
    templateUrl: './purchase-order-voucher-relation-extended-detail.component.html'
})
export class PurchaseOrderVoucherRelationExtendedDetailComponent extends PurchaseOrderVoucherRelationDetailComponent implements OnInit {
    purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
