import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IQuotation } from 'app/shared/model/quotation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { QuotationComponent, QuotationService } from 'app/entities/quotation';

@Component({
    selector: 'jhi-quotation-extended',
    templateUrl: './quotation-extended.component.html'
})
export class QuotationExtendedComponent extends QuotationComponent implements OnInit, OnDestroy {
    quotation: IQuotation;

    constructor(
        protected quotationService: QuotationService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(quotationService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }

    loadAll() {
        if (this.currentSearch) {
            this.quotationService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IQuotation[]>) => this.paginateQuotations(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.quotationService
            .query({
                'requisitionId.equals': this.quotation.requisitionId,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IQuotation[]>) => this.paginateQuotations(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    transition() {
        this.loadAll();
    }
    back() {
        window.history.back();
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
}
