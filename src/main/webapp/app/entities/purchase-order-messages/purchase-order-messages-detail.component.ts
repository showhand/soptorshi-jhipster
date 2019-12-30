import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';

@Component({
    selector: 'jhi-purchase-order-messages-detail',
    templateUrl: './purchase-order-messages-detail.component.html'
})
export class PurchaseOrderMessagesDetailComponent implements OnInit {
    purchaseOrderMessages: IPurchaseOrderMessages;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseOrderMessages }) => {
            this.purchaseOrderMessages = purchaseOrderMessages;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
