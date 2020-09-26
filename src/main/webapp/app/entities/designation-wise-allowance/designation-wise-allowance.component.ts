import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DesignationWiseAllowanceService } from './designation-wise-allowance.service';
import { Designation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation';

@Component({
    selector: 'jhi-designation-wise-allowance',
    templateUrl: './designation-wise-allowance.component.html'
})
export class DesignationWiseAllowanceComponent implements OnInit, OnDestroy {
    designations: Designation[];
    designationWiseAllowances: IDesignationWiseAllowance[];
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
        public designationWiseAllowanceService: DesignationWiseAllowanceService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        public designationService: DesignationService
    ) {
        this.designationWiseAllowances = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        this.designationWiseAllowances = [];

        if (this.currentSearch) {
            this.designationWiseAllowanceService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IDesignationWiseAllowance[]>) => this.paginateDesignationWiseAllowances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        if (this.designationWiseAllowanceService.designationId) {
            this.designationWiseAllowanceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    'designationId.equals': this.designationWiseAllowanceService.designationId,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IDesignationWiseAllowance[]>) => this.paginateDesignationWiseAllowances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.designationWiseAllowances = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.designationWiseAllowances = [];
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
        this.designationWiseAllowances = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = '_score';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    fetch() {
        this.loadAll();
    }

    ngOnInit() {
        this.designationService
            .query({
                page: 0,
                size: 200,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<Designation[]>) => {
                    this.designations = res.body;
                    if (!this.designationWiseAllowanceService.designationId)
                        this.designationWiseAllowanceService.designationId = this.designations[0].id;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        if (this.designationWiseAllowanceService.designationId !== undefined) {
            this.loadAll();
        }
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDesignationWiseAllowances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDesignationWiseAllowance) {
        return item.id;
    }

    registerChangeInDesignationWiseAllowances() {
        this.eventSubscriber = this.eventManager.subscribe('designationWiseAllowanceListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateDesignationWiseAllowances(data: IDesignationWiseAllowance[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.designationWiseAllowances.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
