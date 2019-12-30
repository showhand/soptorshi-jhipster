import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesDetailComponent } from 'app/entities/purchase-order-messages';

@Component({
    selector: 'jhi-purchase-order-messages-detail',
    templateUrl: './purchase-order-messages-extended-detail.component.html'
})
export class PurchaseOrderMessagesExtendedDetailComponent extends PurchaseOrderMessagesDetailComponent implements OnInit {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
