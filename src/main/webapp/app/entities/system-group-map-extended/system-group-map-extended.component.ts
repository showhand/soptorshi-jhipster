import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SystemGroupMapExtendedService } from './system-group-map-extended.service';
import { SystemGroupMapComponent } from 'app/entities/system-group-map';

@Component({
    selector: 'jhi-system-group-map',
    templateUrl: './system-group-map-extended.component.html'
})
export class SystemGroupMapExtendedComponent extends SystemGroupMapComponent implements OnInit, OnDestroy {
    constructor(
        protected systemGroupMapService: SystemGroupMapExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(systemGroupMapService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
