import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RequisitionMessagesExtendedService } from './requisition-messages-extended.service';
import { RequisitionMessagesComponent } from 'app/entities/requisition-messages';

@Component({
    selector: 'jhi-requisition-messages-extended',
    templateUrl: './requisition-messages-extended.component.html'
})
export class RequisitionMessagesExtendedComponent extends RequisitionMessagesComponent implements OnInit, OnDestroy {
    constructor(
        protected requisitionMessagesService: RequisitionMessagesExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(requisitionMessagesService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }

    transition() {
        this.loadAll();
    }
}
