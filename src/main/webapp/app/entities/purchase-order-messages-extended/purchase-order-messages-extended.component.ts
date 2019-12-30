import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PurchaseOrderMessagesExtendedService } from './purchase-order-messages-extended.service';
import { PurchaseOrderMessagesComponent } from 'app/entities/purchase-order-messages';

@Component({
    selector: 'jhi-purchase-order-messages',
    templateUrl: './purchase-order-messages-extended.component.html'
})
export class PurchaseOrderMessagesExtendedComponent extends PurchaseOrderMessagesComponent implements OnInit, OnDestroy {
    constructor(
        purchaseOrderMessagesService: PurchaseOrderMessagesExtendedService,
        parseLinks: JhiParseLinks,
        jhiAlertService: JhiAlertService,
        accountService: AccountService,
        activatedRoute: ActivatedRoute,
        dataUtils: JhiDataUtils,
        router: Router,
        eventManager: JhiEventManager
    ) {
        super(purchaseOrderMessagesService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }
}
