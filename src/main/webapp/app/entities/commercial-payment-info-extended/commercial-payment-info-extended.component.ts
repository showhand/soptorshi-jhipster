import { Component } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { AccountService } from 'app/core';
import { CommercialPaymentInfoExtendedService } from './commercial-payment-info-extended.service';
import { CommercialPaymentInfoComponent } from 'app/entities/commercial-payment-info';

@Component({
    selector: 'jhi-commercial-payment-info-extended',
    templateUrl: './commercial-payment-info-extended.component.html'
})
export class CommercialPaymentInfoExtendedComponent extends CommercialPaymentInfoComponent {
    commercialPaymentInfos: ICommercialPaymentInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;

    constructor(
        protected commercialPaymentInfoService: CommercialPaymentInfoExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialPaymentInfoService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        /*if (this.currentSearch) {
            this.commercialPaymentInfoService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialPaymentInfo[]>) => this.paginateCommercialPaymentInfos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }*/
        if (this.currentSearch) {
            this.commercialPaymentInfoService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'commercialPurchaseOrderPurchaseOrderNo.equals': this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICommercialPaymentInfo[]>) => this.paginateCommercialPaymentInfos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.commercialPaymentInfoService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialPaymentInfo[]>) => this.paginateCommercialPaymentInfos(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.commercialPaymentInfos = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.commercialPaymentInfos = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.commercialPaymentInfos = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = '_score';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCommercialPaymentInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommercialPaymentInfo) {
        return item.id;
    }

    registerChangeInCommercialPaymentInfos() {
        this.eventSubscriber = this.eventManager.subscribe('commercialPaymentInfoListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateCommercialPaymentInfos(data: ICommercialPaymentInfo[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.commercialPaymentInfos.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
