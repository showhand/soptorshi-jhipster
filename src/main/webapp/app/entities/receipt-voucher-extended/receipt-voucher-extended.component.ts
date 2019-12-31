import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ReceiptVoucherExtendedService } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended.service';
import { ReceiptVoucherComponent } from 'app/entities/receipt-voucher';

@Component({
    selector: 'jhi-receipt-voucher',
    templateUrl: './receipt-voucher-extended.component.html'
})
export class ReceiptVoucherExtendedComponent extends ReceiptVoucherComponent implements OnInit, OnDestroy {
    constructor(
        protected receiptVoucherService: ReceiptVoucherExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(receiptVoucherService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
    back() {
        window.history.back();
    }
}
