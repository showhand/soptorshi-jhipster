import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPurchaseOrder, PurchaseOrder } from 'app/shared/model/purchase-order.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PurchaseOrderComponent, PurchaseOrderService } from 'app/entities/purchase-order';
import { PurchaseOrderExtendedService } from 'app/entities/purchase-order-extended/purchase-order-extended.service';
import { IRequisition } from 'app/shared/model/requisition.model';

@Component({
    selector: 'jhi-purchase-order-extended-requisition-directive',
    templateUrl: './purchase-order-extended-requisition-directive.component.html'
})
export class PurchaseOrderExtendedRequisitionDirectiveComponent extends PurchaseOrderComponent implements OnInit, OnDestroy {
    @Input()
    requisition: IRequisition;
    purchaseOrder: IPurchaseOrder;
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

    loadAll() {
        this.purchaseOrderService
            .query({
                'requisitionId.equals': this.requisition.id,
                page: 0,
                size: 100
            })
            .subscribe(
                (res: HttpResponse<IPurchaseOrder[]>) => this.paginatePurchaseOrders(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    clear() {
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.purchaseOrder = new PurchaseOrder();
        this.purchaseOrder.requisitionId = this.requisition.id;
        this.registerChangeInPurchaseOrders();
    }

    transition() {
        this.loadAll();
    }
}
