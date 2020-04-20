import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { AccountService } from 'app/core';
import { SupplyZoneManagerService } from './supply-zone-manager.service';

@Component({
    selector: 'jhi-supply-zone-manager',
    templateUrl: './supply-zone-manager.component.html'
})
export class SupplyZoneManagerComponent implements OnInit, OnDestroy {
    supplyZoneManagers: ISupplyZoneManager[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected supplyZoneManagerService: SupplyZoneManagerService,
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
            this.supplyZoneManagerService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ISupplyZoneManager[]>) => res.ok),
                    map((res: HttpResponse<ISupplyZoneManager[]>) => res.body)
                )
                .subscribe(
                    (res: ISupplyZoneManager[]) => (this.supplyZoneManagers = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.supplyZoneManagerService
            .query()
            .pipe(
                filter((res: HttpResponse<ISupplyZoneManager[]>) => res.ok),
                map((res: HttpResponse<ISupplyZoneManager[]>) => res.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => {
                    this.supplyZoneManagers = res;
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
        this.registerChangeInSupplyZoneManagers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISupplyZoneManager) {
        return item.id;
    }

    registerChangeInSupplyZoneManagers() {
        this.eventSubscriber = this.eventManager.subscribe('supplyZoneManagerListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
