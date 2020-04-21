import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PurchaseOrderVoucherRelationExtendedService } from './purchase-order-voucher-relation-extended.service';
import { PurchaseOrderVoucherRelationComponent } from 'app/entities/purchase-order-voucher-relation';

@Component({
    selector: 'jhi-purchase-order-voucher-relation',
    templateUrl: './purchase-order-voucher-relation-extended.component.html'
})
export class PurchaseOrderVoucherRelationExtendedComponent extends PurchaseOrderVoucherRelationComponent implements OnInit, OnDestroy {
    constructor(
        protected purchaseOrderVoucherRelationService: PurchaseOrderVoucherRelationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(purchaseOrderVoucherRelationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
