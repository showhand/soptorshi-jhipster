import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFineManagement } from 'app/shared/model/fine-management.model';
import { AccountService } from 'app/core';
import { FineManagementService } from './fine-management.service';

@Component({
    selector: 'jhi-fine-management',
    templateUrl: './fine-management.component.html'
})
export class FineManagementComponent implements OnInit, OnDestroy {
    fineManagements: IFineManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected fineManagementService: FineManagementService,
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
            this.fineManagementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IFineManagement[]>) => res.ok),
                    map((res: HttpResponse<IFineManagement[]>) => res.body)
                )
                .subscribe((res: IFineManagement[]) => (this.fineManagements = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.fineManagementService
            .query()
            .pipe(
                filter((res: HttpResponse<IFineManagement[]>) => res.ok),
                map((res: HttpResponse<IFineManagement[]>) => res.body)
            )
            .subscribe(
                (res: IFineManagement[]) => {
                    this.fineManagements = res;
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
        this.registerChangeInFineManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFineManagement) {
        return item.id;
    }

    registerChangeInFineManagements() {
        this.eventSubscriber = this.eventManager.subscribe('fineManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
