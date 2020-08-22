import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { AccountService } from 'app/core';
import { DepreciationCalculationExtendedService } from './depreciation-calculation-extended.service';
import { DepreciationCalculationComponent } from 'app/entities/depreciation-calculation';
import { FinancialAccountYearExtendedService } from 'app/entities/financial-account-year-extended';
import { FinancialYearStatus } from 'app/shared/model/financial-account-year.model';
import { IFinancialAccountYearExtended } from 'app/entities/period-close-extended';

@Component({
    selector: 'jhi-depreciation-calculation',
    templateUrl: './depreciation-calculation-extended.component.html'
})
export class DepreciationCalculationExtendedComponent extends DepreciationCalculationComponent implements OnInit, OnDestroy {
    selectedFinancialAccountYear: IFinancialAccountYearExtended;
    financialAccountYears: IFinancialAccountYearExtended[];

    constructor(
        protected depreciationCalculationService: DepreciationCalculationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        private financialAccountService: FinancialAccountYearExtendedService,
        private router: Router
    ) {
        super(depreciationCalculationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        this.financialAccountService.query({ size: 10000 }).subscribe(res => {
            this.financialAccountYears = res.body;
            this.financialAccountYears.forEach((f: IFinancialAccountYearExtended) => {
                if (f.status === FinancialYearStatus.ACTIVE) {
                    this.selectedFinancialAccountYear = f;
                    this.fetchDepreciationCalculationRecords();
                }
            });
        });
    }

    fetchDepreciationCalculationRecords() {
        this.depreciationCalculationService
            .query({
                'financialAccountYearId.equals': this.selectedFinancialAccountYear.id,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IDepreciationCalculation[]>) => this.paginateDepreciationCalculations(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    addNew(): void {
        const financialAccountYearId = this.selectedFinancialAccountYear.id;
        this.router.navigate(['/depreciation-calculation', financialAccountYearId, 'new']);
    }
}
