import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DepreciationCalculationExtendedService } from './depreciation-calculation-extended.service';
import { DepreciationCalculationComponent } from 'app/entities/depreciation-calculation';

@Component({
    selector: 'jhi-depreciation-calculation',
    templateUrl: './depreciation-calculation-extended.component.html'
})
export class DepreciationCalculationExtendedComponent extends DepreciationCalculationComponent implements OnInit, OnDestroy {
    constructor(
        protected depreciationCalculationService: DepreciationCalculationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(depreciationCalculationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
