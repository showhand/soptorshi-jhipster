import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import {
    PurchaseOrderMessagesExtendedComponent,
    PurchaseOrderMessagesExtendedService
} from 'app/entities/purchase-order-messages-extended';
import { PurchaseOrderMessagesService } from 'app/entities/purchase-order-messages';

@Component({
    selector: 'jhi-purchase-order-messages-directive',
    templateUrl: './purchase-order-messages-extended-directive.component.html'
})
export class PurchaseOrderMessagesExtendedDirectiveComponent extends PurchaseOrderMessagesExtendedComponent implements OnInit, OnDestroy {
    @Input()
    purchaseOrderId: number;

    constructor(
        protected purchaseOrderMessagesService: PurchaseOrderMessagesExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(purchaseOrderMessagesService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
        this.page = 0;
        this.previousPage = 0;
        this.predicate = ['id'];
    }

    loadAll() {
        this.purchaseOrderMessagesService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                'purchaseOrderId.equals': this.purchaseOrderId
            })
            .subscribe(
                (res: HttpResponse<IPurchaseOrderMessages[]>) => this.paginatePurchaseOrderMessages(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    transition() {
        this.loadAll();
    }

    clear() {
        this.loadAll();
    }
}
