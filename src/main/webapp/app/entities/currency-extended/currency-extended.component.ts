import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICurrency } from 'app/shared/model/currency.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CurrencyExtendedService } from './currency-extended.service';
import { CurrencyComponent } from 'app/entities/currency';

@Component({
    selector: 'jhi-currency',
    templateUrl: './currency-extended.component.html'
})
export class CurrencyExtendedComponent extends CurrencyComponent implements OnInit, OnDestroy {
    constructor(
        protected currencyService: CurrencyExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(currencyService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
