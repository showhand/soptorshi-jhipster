import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationExtendedService } from './purchase-order-voucher-relation-extended.service';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { PurchaseOrderVoucherRelationUpdateComponent } from 'app/entities/purchase-order-voucher-relation';
import { JournalVoucherService } from 'app/entities/journal-voucher';
import { ContraVoucherService } from 'app/entities/contra-voucher';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher';
import { PaymentVoucherService } from 'app/entities/payment-voucher';
import { ApplicationType } from 'app/shared/model/journal-voucher.model';

@Component({
    selector: 'jhi-purchase-order-voucher-relation-update',
    templateUrl: './purchase-order-voucher-relation-extended-update.component.html'
})
export class PurchaseOrderVoucherRelationExtendedUpdateComponent extends PurchaseOrderVoucherRelationUpdateComponent implements OnInit {
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderVoucherRelationService: PurchaseOrderVoucherRelationExtendedService,
        protected voucherService: VoucherService,
        protected purchaseOrderService: PurchaseOrderService,
        protected activatedRoute: ActivatedRoute,
        protected route: Router,
        protected journalVoucherService: JournalVoucherService,
        protected contraVoucherService: ContraVoucherService,
        protected receiptVoucherService: ReceiptVoucherService,
        protected paymentVoucherService: PaymentVoucherService
    ) {
        super(jhiAlertService, purchaseOrderVoucherRelationService, voucherService, purchaseOrderService, activatedRoute);
    }

    createJournalVoucher() {
        // const applicationType = new JournalVoucher();
        const applicationType = ApplicationType.PURCHASE_ORDER;
        const applicationId = this.purchaseOrderVoucherRelation.purchaseOrderId;
        this.route.navigate(['/journal-voucher', applicationType, applicationId, 'new']);
    }

    createPaymentVoucher() {
        const applicationType = ApplicationType.PURCHASE_ORDER;
        const applicationId = this.purchaseOrderVoucherRelation.purchaseOrderId;
        this.route.navigate(['/payment-voucher', applicationType, applicationId, 'new']);
    }

    createContraVoucher() {
        const applicationType = ApplicationType.PURCHASE_ORDER;
        const applicationId = this.purchaseOrderVoucherRelation.purchaseOrderId;
        this.route.navigate(['/contra-voucher', applicationType, applicationId, 'new']);
    }

    createReceiptVoucher() {
        const applicationType = ApplicationType.PURCHASE_ORDER;
        const applicationId = this.purchaseOrderVoucherRelation.purchaseOrderId;
        this.route.navigate(['/receipt-voucher', applicationType, applicationId, 'new']);
    }
}
