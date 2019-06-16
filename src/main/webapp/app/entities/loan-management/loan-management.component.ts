import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILoanManagement } from 'app/shared/model/loan-management.model';
import { AccountService } from 'app/core';
import { LoanManagementService } from './loan-management.service';

@Component({
    selector: 'jhi-loan-management',
    templateUrl: './loan-management.component.html'
})
export class LoanManagementComponent implements OnInit, OnDestroy {
    loanManagements: ILoanManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected loanManagementService: LoanManagementService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.loanManagementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ILoanManagement[]>) => res.ok),
                    map((res: HttpResponse<ILoanManagement[]>) => res.body)
                )
                .subscribe((res: ILoanManagement[]) => (this.loanManagements = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.loanManagementService
            .query()
            .pipe(
                filter((res: HttpResponse<ILoanManagement[]>) => res.ok),
                map((res: HttpResponse<ILoanManagement[]>) => res.body)
            )
            .subscribe(
                (res: ILoanManagement[]) => {
                    this.loanManagements = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLoanManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILoanManagement) {
        return item.id;
    }

    registerChangeInLoanManagements() {
        this.eventSubscriber = this.eventManager.subscribe('loanManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
