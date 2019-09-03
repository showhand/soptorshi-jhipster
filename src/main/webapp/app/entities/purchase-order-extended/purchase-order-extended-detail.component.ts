import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderDetailComponent } from 'app/entities/purchase-order';
import { PurchaseOrderExtendedService } from 'app/entities/purchase-order-extended/purchase-order-extended.service';

@Component({
    selector: 'jhi-purchase-order-extended-detail',
    templateUrl: './purchase-order-extended-detail.component.html'
})
export class PurchaseOrderExtendedDetailComponent extends PurchaseOrderDetailComponent implements OnInit {
    purchaseOrder: IPurchaseOrder;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected activatedRoute: ActivatedRoute,
        protected purchaseOrderExtendedService: PurchaseOrderExtendedService
    ) {
        super(dataUtils, activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseOrder }) => {
            this.purchaseOrder = purchaseOrder;
            this.purchaseOrder.purchaseOrderId = this.purchaseOrder.id;
        });
    }

    downloadReport() {
        this.purchaseOrderExtendedService.downloadPurchaseOrderReport(this.purchaseOrder.id);
    }
}
