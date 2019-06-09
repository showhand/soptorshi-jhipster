import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { FineService } from 'app/entities/fine';
import { AdvanceService } from 'app/entities/advance';
import { LoanService } from 'app/entities/loan';
import { ProvidentFundService } from 'app/entities/provident-fund';
import { IFine } from 'app/shared/model/fine.model';
import { ILoan } from 'app/shared/model/loan.model';
import { IAdvance } from 'app/shared/model/advance.model';
import { IProvidentFund } from 'app/shared/model/provident-fund.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-fine-advance-loan-management-detail',
    templateUrl: './fine-advance-loan-management-detail.component.html'
})
export class FineAdvanceLoanManagementDetailComponent implements OnInit {
    fineAdvanceLoanManagement: IFineAdvanceLoanManagement;
    fines: IFine[];
    loans: ILoan[];
    advances: IAdvance[];
    providentFunds: IProvidentFund[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    finePage: any;
    finePreviousPage: any;
    fineReverse: any;
    finePredicate: any;
    loanPage: any;
    loanPreviousPage: any;
    loanReverse: any;
    loanPredicate: any;
    advancePreviousPage: any;
    advancePage: any;
    advanceReverse: any;
    advancePredicate: any;
    providentPage: any;
    providentReverse: any;
    providentPredicate: any;
    providentPreviousPage: any;

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected fineService: FineService,
        protected advanceService: AdvanceService,
        protected loanService: LoanService,
        protected providentFundService: ProvidentFundService,
        protected jhiAlertService: JhiAlertService,
        protected router: Router
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.finePage = data.finePagingParams.page;
            this.finePreviousPage = data.finePagingParams.page;
            this.fineReverse = data.finePagingParams.ascending;
            this.finePredicate = data.finePagingParams.predicate;

            this.loanPage = data.loanPagingParams.page;
            this.loanPreviousPage = data.loanPagingParams.page;
            this.loanReverse = data.loanPagingParams.ascending;
            this.loanPredicate = data.loanPagingParams.predicate;

            this.advancePage = data.advancePagingParams.page;
            this.advancePreviousPage = data.advancePagingParams.page;
            this.advanceReverse = data.advancePagingParams.ascending;
            this.advancePredicate = data.advancePagingParams.predicate;

            this.providentPage = data.providentPagingParams.page;
            this.providentPreviousPage = data.providentPagingParams.page;
            this.providentReverse = data.providentPagingParams.ascending;
            this.providentPredicate = data.providentPagingParams.predicate;
        });
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    loadAll() {
        this.fineService
            .query({
                'employeeId.equals': this.fineAdvanceLoanManagement.id
            })
            .subscribe(
                (response: HttpResponse<IFine[]>) => (this.fines = response.body),
                (response: HttpErrorResponse) => this.onError(response.message)
            );

        this.loanService
            .query({
                'employeeId.equals': this.fineAdvanceLoanManagement.id
            })
            .subscribe(
                (response: HttpResponse<ILoan[]>) => (this.loans = response.body),
                (response: HttpErrorResponse) => this.onError(response.message)
            );

        this.advanceService
            .query({
                'employeeId.equals': this.fineAdvanceLoanManagement.id
            })
            .subscribe(
                (response: HttpResponse<IAdvance[]>) => (this.advances = response.body),
                (response: HttpErrorResponse) => this.onError(response.message)
            );

        this.providentFundService
            .query({
                'employeeId.equals': this.fineAdvanceLoanManagement.id
            })
            .subscribe(
                (response: HttpResponse<IProvidentFund[]>) => (this.providentFunds = response.body),
                (response: HttpErrorResponse) => this.onError(response.message)
            );
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fineAdvanceLoanManagement }) => {
            this.fineAdvanceLoanManagement = fineAdvanceLoanManagement;
            this.loadAll();
        });
    }

    transition() {
        this.router.navigate(['/fine-advance-loan-management', this.fineAdvanceLoanManagement.id, 'view'], {
            queryParams: {
                finePage: this.finePage,
                size: this.itemsPerPage,
                fineSearch: this.currentSearch,
                fineSort: this.finePredicate + ',' + (this.reverse ? 'asc' : 'desc'),

                loanPage: this.loanPage,
                search: this.currentSearch,
                loanSort: this.loanPredicate + ',' + (this.reverse ? 'asc' : 'desc'),

                advancePage: this.advancePage,
                advanceSearch: this.currentSearch,
                advanceSort: this.advancePredicate + ',' + (this.reverse ? 'asc' : 'desc'),

                providentPage: this.providentPage,
                providentSort: this.providentPredicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    previousState() {
        window.history.back();
    }
}
