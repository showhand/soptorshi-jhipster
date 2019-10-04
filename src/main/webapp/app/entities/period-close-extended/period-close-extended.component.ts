import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPeriodClose } from 'app/shared/model/period-close.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PeriodCloseExtendedService } from './period-close-extended.service';
import { PeriodCloseComponent, PeriodCloseService } from 'app/entities/period-close';

@Component({
    selector: 'jhi-period-close-extended',
    templateUrl: './period-close-extended.component.html'
})
export class PeriodCloseExtendedComponent extends PeriodCloseComponent implements OnInit, OnDestroy {
    constructor(
        protected periodCloseService: PeriodCloseExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(periodCloseService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
