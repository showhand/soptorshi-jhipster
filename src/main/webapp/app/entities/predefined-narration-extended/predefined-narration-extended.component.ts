import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PredefinedNarrationExtendedService } from './predefined-narration-extended.service';
import { PredefinedNarrationComponent } from 'app/entities/predefined-narration';

@Component({
    selector: 'jhi-predefined-narration-extended',
    templateUrl: './predefined-narration-extended.component.html'
})
export class PredefinedNarrationExtendedComponent extends PredefinedNarrationComponent implements OnInit, OnDestroy {
    constructor(
        protected predefinedNarrationService: PredefinedNarrationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(predefinedNarrationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
