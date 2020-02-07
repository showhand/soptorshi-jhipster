import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import {
    RequisitionVoucherRelationExtendedComponent,
    RequisitionVoucherRelationExtendedService
} from 'app/entities/requisition-voucher-relation-extended';
import { RequisitionVoucherRelationService } from 'app/entities/requisition-voucher-relation';
import { JournalVoucherService } from 'app/entities/journal-voucher';
import { ContraVoucherService } from 'app/entities/contra-voucher';
import { PaymentVoucherService } from 'app/entities/payment-voucher';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher';
import { JournalVoucherExtendedService } from 'app/entities/journal-voucher-extended';
import { ContraVoucherExtendedService } from 'app/entities/contra-voucher-extended';
import { PaymentVoucherExtendedService } from 'app/entities/payment-voucher-extended';
import { ReceiptVoucherExtendedService } from 'app/entities/receipt-voucher-extended';

@Component({
    selector: 'jhi-purchase-order-requisition-voucher-relation',
    templateUrl: '../requisition-voucher-relation-extended/requisition-voucher-relation-extended.component.html'
})
// tslint:disable-next-line:component-class-suffix
export class PurchaseOrderRequisitionVoucherRelation extends RequisitionVoucherRelationExtendedComponent implements OnInit, OnDestroy {
    constructor(
        protected requisitionVoucherRelationService: RequisitionVoucherRelationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected router: Router,
        protected journalVoucherService: JournalVoucherExtendedService,
        protected contraVoucherService: ContraVoucherExtendedService,
        protected paymentVoucherService: PaymentVoucherExtendedService,
        protected receiptVoucherService: ReceiptVoucherExtendedService
    ) {
        super(
            requisitionVoucherRelationService,
            jhiAlertService,
            eventManager,
            parseLinks,
            activatedRoute,
            accountService,
            router,
            journalVoucherService,
            contraVoucherService,
            paymentVoucherService,
            receiptVoucherService
        );
    }
}
