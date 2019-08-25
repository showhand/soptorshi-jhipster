import { BudgetAllocationComponent, BudgetAllocationService } from 'app/entities/budget-allocation';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';
import { BudgetAllocation, IBudgetAllocation } from 'app/shared/model/budget-allocation.model';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-budget-allocation-extended',
    templateUrl: './budget-allocation-extended.component.html'
})
export class BudgetAllocationExtendedComponent extends BudgetAllocationComponent implements OnInit, OnDestroy {
    budgetAllocation: IBudgetAllocation;
    financialAccountYears: IFinancialAccountYear[];
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
        super(
            budgetAllocationService,
            jhiAlertService,
            eventManager,
            parseLinks,
            activatedRoute,
            accountService,
            financialAccountYearService,
            router
        );
        this.budgetAllocation = new BudgetAllocation();
        this.budgetAllocation.financialAccountYearId = this.budgetAllocationService.financialAccountYearId;
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

    registerChangeInBudgetAllocations() {
        const eventS = this.eventManager;
        const eventSS = this.eventSubscriber;
        this.eventSubscriber = this.eventManager.subscribe('budgetAllocationListModification', response => {
            console.log('event scriber response');
            console.log(response);
            this.reset();
        });
    }
}
