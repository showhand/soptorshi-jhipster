import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { VoucherNumberControlExtendedService } from './voucher-number-control-extended.service';
import { VoucherNumberControlComponent } from 'app/entities/voucher-number-control';
import { FinancialYearStatus, IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { IFinancialAccountYearExtended } from 'app/entities/period-close-extended';
import { FinancialAccountYearExtendedService } from 'app/entities/financial-account-year-extended';

@Component({
    selector: 'jhi-voucher-number-control',
    templateUrl: './voucher-number-control-extended.component.html'
})
export class VoucherNumberControlExtendedComponent extends VoucherNumberControlComponent implements OnInit, OnDestroy {
    financialAccountYears: IFinancialAccountYear[];
    selectedFinancialAccountYear: IFinancialAccountYearExtended;
    constructor(
        protected voucherNumberControlService: VoucherNumberControlExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected financialAccountYearExtendedService: FinancialAccountYearExtendedService
    ) {
        super(voucherNumberControlService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVoucherNumberControls();

        this.financialAccountYearExtendedService.query({}).subscribe((response: HttpResponse<IFinancialAccountYear[]>) => {
            this.financialAccountYears = response.body;
            this.financialAccountYears.forEach((f: IFinancialAccountYear) => {
                if (f.status === FinancialYearStatus.ACTIVE) {
                    this.selectedFinancialAccountYear = f;
                    this.selectedFinancialAccountYear.financialAccountYearId = f.id;
                    this.loadAll();
                }
            });
        });
    }

    loadAll() {
        this.voucherNumberControlService
            .query({
                'financialAccountYearId.equals': this.selectedFinancialAccountYear.id,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IVoucherNumberControl[]>) => this.paginateVoucherNumberControls(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    financialAccountYearSelectionChanged() {
        this.selectedFinancialAccountYear.financialAccountYearId = this.selectedFinancialAccountYear.id;
    }
}
