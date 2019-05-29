import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPayrollManagement } from 'app/shared/model/payroll-management.model';
import { AccountService } from 'app/core';
import { PayrollManagementService } from './payroll-management.service';

@Component({
    selector: 'jhi-payroll-management',
    templateUrl: './payroll-management.component.html'
})
export class PayrollManagementComponent implements OnInit, OnDestroy {
    payrollManagements: IPayrollManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected payrollManagementService: PayrollManagementService,
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
            this.payrollManagementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IPayrollManagement[]>) => res.ok),
                    map((res: HttpResponse<IPayrollManagement[]>) => res.body)
                )
                .subscribe(
                    (res: IPayrollManagement[]) => (this.payrollManagements = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.payrollManagementService
            .query()
            .pipe(
                filter((res: HttpResponse<IPayrollManagement[]>) => res.ok),
                map((res: HttpResponse<IPayrollManagement[]>) => res.body)
            )
            .subscribe(
                (res: IPayrollManagement[]) => {
                    this.payrollManagements = res;
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
        this.registerChangeInPayrollManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPayrollManagement) {
        return item.id;
    }

    registerChangeInPayrollManagements() {
        this.eventSubscriber = this.eventManager.subscribe('payrollManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
