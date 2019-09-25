import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { FinancialAccountYearExtendedService } from './financial-account-year-extended.service';
import { FinancialAccountYearComponent } from 'app/entities/financial-account-year';

@Component({
    selector: 'jhi-financial-account-year',
    templateUrl: './financial-account-year-extended.component.html'
})
export class FinancialAccountYearExtendedComponent extends FinancialAccountYearComponent implements OnInit, OnDestroy {
    constructor(
        protected financialAccountYearService: FinancialAccountYearExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(financialAccountYearService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
}
