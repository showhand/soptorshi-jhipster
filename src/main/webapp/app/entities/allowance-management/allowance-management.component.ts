import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAllowanceManagement } from 'app/shared/model/allowance-management.model';
import { AccountService } from 'app/core';
import { AllowanceManagementService } from './allowance-management.service';

@Component({
    selector: 'jhi-allowance-management',
    templateUrl: './allowance-management.component.html'
})
export class AllowanceManagementComponent implements OnInit, OnDestroy {
    allowanceManagements: IAllowanceManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected allowanceManagementService: AllowanceManagementService,
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
            this.allowanceManagementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IAllowanceManagement[]>) => res.ok),
                    map((res: HttpResponse<IAllowanceManagement[]>) => res.body)
                )
                .subscribe(
                    (res: IAllowanceManagement[]) => (this.allowanceManagements = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.allowanceManagementService
            .query()
            .pipe(
                filter((res: HttpResponse<IAllowanceManagement[]>) => res.ok),
                map((res: HttpResponse<IAllowanceManagement[]>) => res.body)
            )
            .subscribe(
                (res: IAllowanceManagement[]) => {
                    this.allowanceManagements = res;
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
        this.registerChangeInAllowanceManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAllowanceManagement) {
        return item.id;
    }

    registerChangeInAllowanceManagements() {
        this.eventSubscriber = this.eventManager.subscribe('allowanceManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
