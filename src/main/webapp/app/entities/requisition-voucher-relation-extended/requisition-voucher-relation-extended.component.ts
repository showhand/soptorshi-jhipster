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
        protected journalVoucherService: JournalVoucherService,
        protected contraVoucherService: ContraVoucherService,
        protected paymentVoucherService: PaymentVoucherService,
        protected receiptVoucherService: ReceiptVoucherService
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
    viewVoucher(requisitionVoucherRelation: IRequisitionVoucherRelation) {
        if (requisitionVoucherRelation.voucherNo.includes('JN')) {
            this.router.navigate(['/journal-voucher', requisitionVoucherRelation.voucherNo]);
        }
    }
}
