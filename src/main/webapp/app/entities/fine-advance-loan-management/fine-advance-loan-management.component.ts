import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { AccountService } from 'app/core';
import { FineAdvanceLoanManagementService } from './fine-advance-loan-management.service';

@Component({
    selector: 'jhi-fine-advance-loan-management',
    templateUrl: './fine-advance-loan-management.component.html'
})
export class FineAdvanceLoanManagementComponent implements OnInit, OnDestroy {
    fineAdvanceLoanManagements: IFineAdvanceLoanManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected fineAdvanceLoanManagementService: FineAdvanceLoanManagementService,
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
            this.fineAdvanceLoanManagementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IFineAdvanceLoanManagement[]>) => res.ok),
                    map((res: HttpResponse<IFineAdvanceLoanManagement[]>) => res.body)
                )
                .subscribe(
                    (res: IFineAdvanceLoanManagement[]) => (this.fineAdvanceLoanManagements = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.fineAdvanceLoanManagementService
            .query()
            .pipe(
                filter((res: HttpResponse<IFineAdvanceLoanManagement[]>) => res.ok),
                map((res: HttpResponse<IFineAdvanceLoanManagement[]>) => res.body)
            )
            .subscribe(
                (res: IFineAdvanceLoanManagement[]) => {
                    this.fineAdvanceLoanManagements = res;
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
        this.registerChangeInFineAdvanceLoanManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFineAdvanceLoanManagement) {
        return item.id;
    }

    registerChangeInFineAdvanceLoanManagements() {
        this.eventSubscriber = this.eventManager.subscribe('fineAdvanceLoanManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
