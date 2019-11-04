import { Component } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { AccountService } from 'app/core';
import { CommercialProformaInvoiceExtendedService } from './commercial-proforma-invoice-extended.service';
import { CommercialProformaInvoiceComponent } from 'app/entities/commercial-proforma-invoice';

@Component({
    selector: 'jhi-commercial-proforma-invoice-extended',
    templateUrl: './commercial-proforma-invoice-extended.component.html'
})
export class CommercialProformaInvoiceExtendedComponent extends CommercialProformaInvoiceComponent {
    commercialProformaInvoices: ICommercialProformaInvoice[];
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
        protected commercialProformaInvoiceService: CommercialProformaInvoiceExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialProformaInvoiceService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        /*if (this.currentSearch) {
            this.commercialProformaInvoiceService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialProformaInvoice[]>) => this.paginateCommercialProformaInvoices(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }*/
        if (this.currentSearch) {
            this.commercialProformaInvoiceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'commercialPurchaseOrderPurchaseOrderNo.equals': this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICommercialProformaInvoice[]>) => this.paginateCommercialProformaInvoices(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.commercialProformaInvoiceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialProformaInvoice[]>) => this.paginateCommercialProformaInvoices(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.commercialProformaInvoices = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.commercialProformaInvoices = [];
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
        this.commercialProformaInvoices = [];
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
        this.registerChangeInCommercialProformaInvoices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommercialProformaInvoice) {
        return item.id;
    }

    registerChangeInCommercialProformaInvoices() {
        this.eventSubscriber = this.eventManager.subscribe('commercialProformaInvoiceListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateCommercialProformaInvoices(data: ICommercialProformaInvoice[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.commercialProformaInvoices.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
