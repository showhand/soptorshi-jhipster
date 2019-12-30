import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RequisitionMessagesComponent } from 'app/entities/requisition-messages';
import { RequisitionMessagesExtendedComponent, RequisitionMessagesExtendedService } from 'app/entities/requisition-messages-extended';

@Component({
    selector: 'jhi-requisition-messages-extended-directive',
    templateUrl: './requisition-messages-directive.component.html'
})
export class RequisitionMessagesDirectiveComponent extends RequisitionMessagesExtendedComponent implements OnInit, OnDestroy {
    @Input()
    requisitionId: number;
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
        this.page = 0;
        this.previousPage = 0;
        this.predicate = ['id'];
    }
    loadAll() {
        this.requisitionMessagesService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                'requisitionId.equals': this.requisitionId
            })
            .subscribe(
                (res: HttpResponse<IRequisitionMessages[]>) => this.paginateRequisitionMessages(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    transition() {
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRequisitionMessages();
    }
}
