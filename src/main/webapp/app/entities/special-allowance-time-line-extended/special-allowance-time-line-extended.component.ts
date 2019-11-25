import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SpecialAllowanceTimeLineExtendedService } from './special-allowance-time-line-extended.service';
import { SpecialAllowanceTimeLineComponent } from 'app/entities/special-allowance-time-line';

@Component({
    selector: 'jhi-special-allowance-time-line',
    templateUrl: './special-allowance-time-line-extended.component.html'
})
export class SpecialAllowanceTimeLineExtendedComponent extends SpecialAllowanceTimeLineComponent implements OnInit, OnDestroy {
    currentAccount: any;
    specialAllowanceTimeLines: ISpecialAllowanceTimeLine[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        protected specialAllowanceTimeLineService: SpecialAllowanceTimeLineExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(specialAllowanceTimeLineService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
}
