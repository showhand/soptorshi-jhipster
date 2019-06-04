import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAdvanceManagement } from 'app/shared/model/advance-management.model';
import { AccountService } from 'app/core';
import { AdvanceManagementService } from './advance-management.service';

@Component({
    selector: 'jhi-advance-management',
    templateUrl: './advance-management.component.html'
})
export class AdvanceManagementComponent implements OnInit, OnDestroy {
    advanceManagements: IAdvanceManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected advanceManagementService: AdvanceManagementService,
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
            this.advanceManagementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IAdvanceManagement[]>) => res.ok),
                    map((res: HttpResponse<IAdvanceManagement[]>) => res.body)
                )
                .subscribe(
                    (res: IAdvanceManagement[]) => (this.advanceManagements = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.advanceManagementService
            .query()
            .pipe(
                filter((res: HttpResponse<IAdvanceManagement[]>) => res.ok),
                map((res: HttpResponse<IAdvanceManagement[]>) => res.body)
            )
            .subscribe(
                (res: IAdvanceManagement[]) => {
                    this.advanceManagements = res;
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
        this.registerChangeInAdvanceManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAdvanceManagement) {
        return item.id;
    }

    registerChangeInAdvanceManagements() {
        this.eventSubscriber = this.eventManager.subscribe('advanceManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
