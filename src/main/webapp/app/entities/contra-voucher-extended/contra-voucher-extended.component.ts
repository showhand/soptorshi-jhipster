import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ContraVoucherExtendedService } from 'app/entities/contra-voucher-extended/contra-voucher-extended.service';
import { ContraVoucherComponent } from 'app/entities/contra-voucher';

@Component({
    selector: 'jhi-contra-voucher',
    templateUrl: './contra-voucher-extended.component.html'
})
export class ContraVoucherExtendedComponent extends ContraVoucherComponent implements OnInit, OnDestroy {
    constructor(
        protected contraVoucherService: ContraVoucherExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(contraVoucherService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }

    back() {
        window.history.back();
    }
}
