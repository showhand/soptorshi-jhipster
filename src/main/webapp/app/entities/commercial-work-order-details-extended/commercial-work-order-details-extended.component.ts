import { Component } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { AccountService } from 'app/core';
import { CommercialWorkOrderDetailsExtendedService } from './commercial-work-order-details-extended.service';
import { CommercialWorkOrderDetailsComponent } from 'app/entities/commercial-work-order-details';

@Component({
    selector: 'jhi-commercial-work-order-details-extended',
    templateUrl: './commercial-work-order-details-extended.component.html'
})
export class CommercialWorkOrderDetailsExtendedComponent extends CommercialWorkOrderDetailsComponent {
    commercialWorkOrderDetails: ICommercialWorkOrderDetails[];
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
        protected commercialWorkOrderDetailsService: CommercialWorkOrderDetailsExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialWorkOrderDetailsService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        /*if (this.currentSearch) {
            this.commercialWorkOrderDetailsService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialWorkOrderDetails[]>) => this.paginateCommercialWorkOrderDetails(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }*/
        if (this.currentSearch) {
            this.commercialWorkOrderDetailsService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'commercialWorkOrderRefNo.equals': this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICommercialWorkOrderDetails[]>) => this.paginateCommercialWorkOrderDetails(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.commercialWorkOrderDetailsService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialWorkOrderDetails[]>) => this.paginateCommercialWorkOrderDetails(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.commercialWorkOrderDetails = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.commercialWorkOrderDetails = [];
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
        this.commercialWorkOrderDetails = [];
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
        this.registerChangeInCommercialWorkOrderDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommercialWorkOrderDetails) {
        return item.id;
    }

    registerChangeInCommercialWorkOrderDetails() {
        this.eventSubscriber = this.eventManager.subscribe('commercialWorkOrderDetailsListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateCommercialWorkOrderDetails(data: ICommercialWorkOrderDetails[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.commercialWorkOrderDetails.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
