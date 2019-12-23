import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';
import { AccountService } from 'app/core';
import { MontlySalaryVouchersService } from './montly-salary-vouchers.service';

@Component({
    selector: 'jhi-montly-salary-vouchers',
    templateUrl: './montly-salary-vouchers.component.html'
})
export class MontlySalaryVouchersComponent implements OnInit, OnDestroy {
    montlySalaryVouchers: IMontlySalaryVouchers[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected montlySalaryVouchersService: MontlySalaryVouchersService,
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
            this.montlySalaryVouchersService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IMontlySalaryVouchers[]>) => res.ok),
                    map((res: HttpResponse<IMontlySalaryVouchers[]>) => res.body)
                )
                .subscribe(
                    (res: IMontlySalaryVouchers[]) => (this.montlySalaryVouchers = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.montlySalaryVouchersService
            .query()
            .pipe(
                filter((res: HttpResponse<IMontlySalaryVouchers[]>) => res.ok),
                map((res: HttpResponse<IMontlySalaryVouchers[]>) => res.body)
            )
            .subscribe(
                (res: IMontlySalaryVouchers[]) => {
                    this.montlySalaryVouchers = res;
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
        this.registerChangeInMontlySalaryVouchers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMontlySalaryVouchers) {
        return item.id;
    }

    registerChangeInMontlySalaryVouchers() {
        this.eventSubscriber = this.eventManager.subscribe('montlySalaryVouchersListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
