import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DtTransactionExtendedService } from './dt-transaction-extended.service';
import { DtTransactionComponent } from 'app/entities/dt-transaction';

@Component({
    selector: 'jhi-dt-transaction',
    templateUrl: './dt-transaction-extended.component.html'
})
export class DtTransactionExtendedComponent extends DtTransactionComponent implements OnInit, OnDestroy {
    constructor(
        protected dtTransactionService: DtTransactionExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(dtTransactionService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }

    transition() {
        this.loadAll();
    }

    registerChangeInDtTransactions() {
        this.eventSubscriber = this.eventManager.subscribe(null, response => this.loadAll());
    }
}
