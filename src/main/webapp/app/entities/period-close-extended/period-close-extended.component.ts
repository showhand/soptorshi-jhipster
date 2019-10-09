import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { PeriodCloseExtendedService } from './period-close-extended.service';
import { PeriodCloseComponent } from 'app/entities/period-close';
import { FinancialAccountYearExtendedService } from 'app/entities/financial-account-year-extended';
import { FinancialYearStatus, IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { IPeriodClose } from 'app/shared/model/period-close.model';
import { Moment } from 'moment';
import { IFinancialAccountYearExtended } from 'app/entities/period-close-extended/period-close-extended.route';

@Component({
    selector: 'jhi-period-close-extended',
    templateUrl: './period-close-extended.component.html'
})
export class PeriodCloseExtendedComponent extends PeriodCloseComponent implements OnInit, OnDestroy {
    financialAccountYears: IFinancialAccountYear[];
    selectedFinancialAccountYear: IFinancialAccountYearExtended;
    constructor(
        protected periodCloseService: PeriodCloseExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected financialAccountYearExtendedService: FinancialAccountYearExtendedService
    ) {
        super(periodCloseService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriodCloses();

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

    financialAccountYearSelectionChanged() {
        this.selectedFinancialAccountYear.financialAccountYearId = this.selectedFinancialAccountYear.id;
    }

    loadAll() {
        this.periodCloseService
            .query({
                'financialAccountYearId.equals': this.selectedFinancialAccountYear.id,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IPeriodClose[]>) => this.paginatePeriodCloses(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
