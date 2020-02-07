import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RequisitionVoucherRelationExtendedService } from './requisition-voucher-relation-extended.service';
import { RequisitionVoucherRelationComponent } from 'app/entities/requisition-voucher-relation';
import { JournalVoucherService } from 'app/entities/journal-voucher';
import { ContraVoucherService } from 'app/entities/contra-voucher';
import { PaymentVoucherService } from 'app/entities/payment-voucher';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher';
import { JournalVoucherExtendedService } from 'app/entities/journal-voucher-extended';
import { ContraVoucherExtendedService } from 'app/entities/contra-voucher-extended';
import { PaymentVoucherExtendedService } from 'app/entities/payment-voucher-extended';
import { ReceiptVoucherExtendedService } from 'app/entities/receipt-voucher-extended';

@Component({
    selector: 'jhi-requisition-voucher-relation-extended',
    templateUrl: './requisition-voucher-relation-extended.component.html'
})
export class RequisitionVoucherRelationExtendedComponent extends RequisitionVoucherRelationComponent implements OnInit, OnDestroy {
    @Input()
    requisitionId: number;
    @Input()
    disableEdit: boolean;
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
        super(requisitionVoucherRelationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        this.requisitionVoucherRelationService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                'requisitionId.equals': this.requisitionId
            })
            .subscribe(
                (res: HttpResponse<IRequisitionVoucherRelation[]>) => this.paginateRequisitionVoucherRelations(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    goToVoucher(voucherNo: string) {
        if (voucherNo.includes('JN')) {
            this.journalVoucherService.findByVoucherNo(voucherNo).subscribe(res => {
                this.router.navigate(['/journal-voucher', res.body.id, 'edit']);
            });
        } else if (voucherNo.includes('BP')) {
            this.paymentVoucherService.findByVoucherNo(voucherNo).subscribe(res => {
                this.router.navigate(['/payment-voucher', res.body.id, 'edit']);
            });
        }
    }
}
