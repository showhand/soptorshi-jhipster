import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TermsAndConditionsComponent, TermsAndConditionsService } from 'app/entities/terms-and-conditions';

@Component({
    selector: 'jhi-terms-and-conditions-extended',
    templateUrl: './terms-and-conditions-extended.component.html'
})
export class TermsAndConditionsExtendedComponent extends TermsAndConditionsComponent implements OnInit, OnDestroy {
    termsAndCondition: ITermsAndConditions;
    constructor(
        protected termsAndConditionsService: TermsAndConditionsService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(termsAndConditionsService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }

    loadAll() {
        if (this.currentSearch) {
            this.termsAndConditionsService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ITermsAndConditions[]>) => this.paginateTermsAndConditions(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.termsAndConditionsService
            .query({
                'purchaseOrderId.equals': this.termsAndCondition.purchaseOrderId,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ITermsAndConditions[]>) => this.paginateTermsAndConditions(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    back() {
        window.history.back();
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ termsAndConditions }) => {
            this.termsAndCondition = termsAndConditions;
            this.loadAll();
        });
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTermsAndConditions();
    }
}
