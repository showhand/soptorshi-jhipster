import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';

@Component({
    selector: 'jhi-purchase-order-voucher-relation-detail',
    templateUrl: './purchase-order-voucher-relation-detail.component.html'
})
export class PurchaseOrderVoucherRelationDetailComponent implements OnInit {
    purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseOrderVoucherRelation }) => {
            this.purchaseOrderVoucherRelation = purchaseOrderVoucherRelation;
        });
    }

    previousState() {
        window.history.back();
    }
}
