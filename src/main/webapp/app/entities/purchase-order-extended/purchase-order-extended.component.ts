import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PurchaseOrderComponent, PurchaseOrderService } from 'app/entities/purchase-order';
import { PurchaseOrderExtendedService } from 'app/entities/purchase-order-extended/purchase-order-extended.service';

@Component({
    selector: 'jhi-purchase-order-extended',
    templateUrl: './purchase-order-extended.component.html'
})
export class PurchaseOrderExtendedComponent extends PurchaseOrderComponent implements OnInit, OnDestroy {
    constructor(
        protected purchaseOrderService: PurchaseOrderExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(purchaseOrderService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }
}
