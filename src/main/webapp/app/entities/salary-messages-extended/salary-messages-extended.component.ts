import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SalaryMessagesExtendedService } from './salary-messages-extended.service';
import { SalaryMessagesComponent } from 'app/entities/salary-messages';

@Component({
    selector: 'jhi-salary-messages',
    templateUrl: './salary-messages-extended.component.html'
})
export class SalaryMessagesExtendedComponent extends SalaryMessagesComponent implements OnInit, OnDestroy {
    constructor(
        protected salaryMessagesService: SalaryMessagesExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(salaryMessagesService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }
}
