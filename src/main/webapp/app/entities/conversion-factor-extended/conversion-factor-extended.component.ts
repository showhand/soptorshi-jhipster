import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ConversionFactorExtendedService } from './conversion-factor-extended.service';
import { ConversionFactorComponent } from 'app/entities/conversion-factor';

@Component({
    selector: 'jhi-conversion-factor',
    templateUrl: './conversion-factor-extended.component.html'
})
export class ConversionFactorExtendedComponent extends ConversionFactorComponent implements OnInit, OnDestroy {
    constructor(
        protected conversionFactorService: ConversionFactorExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(conversionFactorService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
