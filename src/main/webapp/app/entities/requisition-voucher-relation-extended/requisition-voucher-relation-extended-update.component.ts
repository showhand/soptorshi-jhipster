import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationExtendedService } from './requisition-voucher-relation-extended.service';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { RequisitionVoucherRelationUpdateComponent } from 'app/entities/requisition-voucher-relation';
import { VoucherService } from 'app/entities/voucher';
import { JournalVoucherService } from 'app/entities/journal-voucher';
import { ContraVoucherService } from 'app/entities/contra-voucher';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher';
import { PaymentVoucherService } from 'app/entities/payment-voucher';
import { ApplicationType } from 'app/shared/model/journal-voucher.model';

@Component({
    selector: 'jhi-requisition-voucher-relation-update',
    templateUrl: './requisition-voucher-relation-extended-update.component.html'
})
export class RequisitionVoucherRelationExtendedUpdateComponent extends RequisitionVoucherRelationUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionVoucherRelationService: RequisitionVoucherRelationExtendedService,
        protected voucherService: VoucherService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute,
        protected route: Router,
        protected journalVoucherService: JournalVoucherService,
        protected contraVoucherService: ContraVoucherService,
        protected receiptVoucherService: ReceiptVoucherService,
        protected paymentVoucherService: PaymentVoucherService
    ) {
        super(jhiAlertService, requisitionVoucherRelationService, voucherService, requisitionService, activatedRoute);
    }

    createJournalVoucher() {
        // const applicationType = new JournalVoucher();
        const applicationType = ApplicationType.REQUISITION;
        const applicationId = this.requisitionVoucherRelation.requisitionId;
        this.route.navigate(['/journal-voucher', applicationType, applicationId, 'new']);
    }

    createPaymentVoucher() {
        const applicationType = ApplicationType.REQUISITION;
        const applicationId = this.requisitionVoucherRelation.requisitionId;
        this.route.navigate(['/payment-voucher', applicationType, applicationId, 'new']);
    }

    createContraVoucher() {
        const applicationType = ApplicationType.REQUISITION;
        const applicationId = this.requisitionVoucherRelation.requisitionId;
        this.route.navigate(['/contra-voucher', applicationType, applicationId, 'new']);
    }

    createReceiptVoucher() {
        const applicationType = ApplicationType.REQUISITION;
        const applicationId = this.requisitionVoucherRelation.requisitionId;
        this.route.navigate(['/receipt-voucher', applicationType, applicationId, 'new']);
    }
}
