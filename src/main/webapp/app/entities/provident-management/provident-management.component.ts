import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProvidentManagement } from 'app/shared/model/provident-management.model';
import { AccountService } from 'app/core';
import { ProvidentManagementService } from './provident-management.service';

@Component({
    selector: 'jhi-provident-management',
    templateUrl: './provident-management.component.html'
})
export class ProvidentManagementComponent implements OnInit, OnDestroy {
    providentManagements: IProvidentManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected providentManagementService: ProvidentManagementService,
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
            this.providentManagementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IProvidentManagement[]>) => res.ok),
                    map((res: HttpResponse<IProvidentManagement[]>) => res.body)
                )
                .subscribe(
                    (res: IProvidentManagement[]) => (this.providentManagements = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.providentManagementService
            .query()
            .pipe(
                filter((res: HttpResponse<IProvidentManagement[]>) => res.ok),
                map((res: HttpResponse<IProvidentManagement[]>) => res.body)
            )
            .subscribe(
                (res: IProvidentManagement[]) => {
                    this.providentManagements = res;
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
        this.registerChangeInProvidentManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProvidentManagement) {
        return item.id;
    }

    registerChangeInProvidentManagements() {
        this.eventSubscriber = this.eventManager.subscribe('providentManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
