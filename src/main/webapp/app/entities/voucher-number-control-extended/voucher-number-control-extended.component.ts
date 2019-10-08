import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { VoucherNumberControlExtendedService } from './voucher-number-control-extended.service';
import { VoucherNumberControlComponent } from 'app/entities/voucher-number-control';

@Component({
    selector: 'jhi-voucher-number-control',
    templateUrl: './voucher-number-control-extended.component.html'
})
export class VoucherNumberControlExtendedComponent extends VoucherNumberControlComponent implements OnInit, OnDestroy {
    constructor(
        protected voucherNumberControlService: VoucherNumberControlExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(voucherNumberControlService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
}
