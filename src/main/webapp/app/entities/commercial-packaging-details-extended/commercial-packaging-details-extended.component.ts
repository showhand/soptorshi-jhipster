import { Component } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { AccountService } from 'app/core';
import { CommercialPackagingDetailsExtendedService } from './commercial-packaging-details-extended.service';
import { CommercialPackagingDetailsComponent } from 'app/entities/commercial-packaging-details';

@Component({
    selector: 'jhi-commercial-packaging-details-extended',
    templateUrl: './commercial-packaging-details-extended.component.html'
})
export class CommercialPackagingDetailsExtendedComponent extends CommercialPackagingDetailsComponent {
    commercialPackagingDetails: ICommercialPackagingDetails[];
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
        protected commercialPackagingDetailsService: CommercialPackagingDetailsExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialPackagingDetailsService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        /*if (this.currentSearch) {
            this.commercialPackagingDetailsService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialPackagingDetails[]>) => this.paginateCommercialPackagingDetails(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }*/
        if (this.currentSearch) {
            this.commercialPackagingDetailsService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'commercialPackagingConsignmentNo.equals': this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICommercialPackagingDetails[]>) => this.paginateCommercialPackagingDetails(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.commercialPackagingDetailsService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICommercialPackagingDetails[]>) => this.paginateCommercialPackagingDetails(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.commercialPackagingDetails = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.commercialPackagingDetails = [];
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
        this.commercialPackagingDetails = [];
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
        this.registerChangeInCommercialPackagingDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommercialPackagingDetails) {
        return item.id;
    }

    registerChangeInCommercialPackagingDetails() {
        this.eventSubscriber = this.eventManager.subscribe('commercialPackagingDetailsListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateCommercialPackagingDetails(data: ICommercialPackagingDetails[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.commercialPackagingDetails.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
