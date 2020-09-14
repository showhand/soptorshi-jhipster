import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PaymentVoucherComponent, PaymentVoucherService } from 'app/entities/payment-voucher';
import { PaymentVoucherExtendedService } from 'app/entities/payment-voucher-extended/payment-voucher-extended.service';

@Component({
    selector: 'jhi-payment-voucher-extended',
    templateUrl: './payment-voucher-extended.component.html'
})
export class PaymentVoucherExtendedComponent extends PaymentVoucherComponent implements OnInit, OnDestroy {
    constructor(
        protected paymentVoucherService: PaymentVoucherExtendedService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(paymentVoucherService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }

    ngOnDestroy() {
        //   this.eventManager.destroy(this.eventSubscriber);
    }

    back() {
        window.history.back();
    }
}
