import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IMstAccount } from 'app/shared/model/mst-account.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { MstAccountExtendedService } from 'app/entities/mst-account-extended/mst-account-extended.service';
import { MstAccountComponent, MstAccountService } from 'app/entities/mst-account';

@Component({
    selector: 'jhi-mst-account-extended',
    templateUrl: './mst-account-extended.component.html'
})
export class MstAccountExtendedComponent extends MstAccountComponent implements OnInit, OnDestroy {
    constructor(
        protected mstAccountService: MstAccountExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(mstAccountService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }

    downloadChartsOfAccount() {
        this.mstAccountService.chartsOfAccount();
    }
}
