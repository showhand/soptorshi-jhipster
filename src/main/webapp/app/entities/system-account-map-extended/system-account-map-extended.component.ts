import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SystemAccountMapExtendedService } from './system-account-map-extended.service';
import { SystemAccountMapComponent } from 'app/entities/system-account-map';

@Component({
    selector: 'jhi-system-account-map',
    templateUrl: './system-account-map-extended.component.html'
})
export class SystemAccountMapExtendedComponent extends SystemAccountMapComponent implements OnInit, OnDestroy {
    constructor(
        protected systemAccountMapService: SystemAccountMapExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(systemAccountMapService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
