import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { BudgetAllocation, IBudgetAllocation } from 'app/shared/model/budget-allocation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { BudgetAllocationService } from './budget-allocation.service';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { BudgetParams } from 'app/entities/budget-allocation/budget-allocation.route';

@Component({
    selector: 'jhi-budget-allocation',
    templateUrl: './budget-allocation.component.html'
})
export class BudgetAllocationComponent implements OnInit, OnDestroy {
    budgetAllocation: IBudgetAllocation;
    budgetAllocations: IBudgetAllocation[];
    financialAccountYears: IFinancialAccountYear[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
    routeData: any;

    constructor(
        public budgetAllocationService: BudgetAllocationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected router: Router
    ) {
        this.budgetAllocation = new BudgetAllocation();
        this.budgetAllocation.financialAccountYearId = this.budgetAllocationService.financialAccountYearId;
        this.budgetAllocations = [];
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
        /*if (this.currentSearch) {
            this.budgetAllocationService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IBudgetAllocation[]>) => this.paginateBudgetAllocations(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }*/

        this.financialAccountYearService
            .query({
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IFinancialAccountYear[]>) => {
                    this.financialAccountYears = res.body;
                    if (!this.budgetAllocationService.financialAccountYearId) {
                        this.budgetAllocationService.financialAccountYearId = this.financialAccountYears[0].id;
                    }
                    this.fetchBudgetAllocationInformation();
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    hideSelect() {
        this.budgetAllocationService.showSelect = !this.budgetAllocationService.showSelect;
    }

    fetchBudgetAllocationInformation() {
        if (this.budgetAllocationService.financialAccountYearId) {
            this.budgetAllocationService
                .query({
                    'financialAccountYearId.equals': this.budgetAllocationService.financialAccountYearId,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IBudgetAllocation[]>) => this.paginateBudgetAllocations(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.budgetAllocationService.showSelect = true;
        this.budgetAllocationService.selectColumn = 'col-4';
        this.budgetAllocationService.detailsColumn = 'col-8';
        this.page = 0;
        this.budgetAllocations = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.budgetAllocations = [];
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
        this.budgetAllocations = [];
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
        this.budgetAllocationService.showSelect =
            this.budgetAllocationService.showSelect == null ? true : this.budgetAllocationService.showSelect;
        this.budgetAllocationService.selectColumn =
            this.budgetAllocationService.selectColumn == null ? 'col-4' : this.budgetAllocationService.selectColumn;
        this.budgetAllocationService.detailsColumn =
            this.budgetAllocationService.detailsColumn == null ? 'col-8' : this.budgetAllocationService.detailsColumn;
        this.loadAll();

        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBudgetAllocations();
    }

    fetch() {
        this.budgetAllocations = [];
        this.budgetAllocationService.showSelect = false;
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBudgetAllocation) {
        return item.id;
    }

    registerChangeInBudgetAllocations() {
        const eventS = this.eventManager;
        const eventSS = this.eventSubscriber;
        this.eventSubscriber = this.eventManager.subscribe('budgetAllocationListModification', response => {
            console.log('event scriber response');
            console.log(response);
            this.reset();
        });
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateBudgetAllocations(data: IBudgetAllocation[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.budgetAllocations.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
