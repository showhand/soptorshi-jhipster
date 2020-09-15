import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPurchaseOrderVoucherRelation, PurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PurchaseOrderVoucherRelationExtendedService } from './purchase-order-voucher-relation-extended.service';
import { PurchaseOrderVoucherRelationComponent } from 'app/entities/purchase-order-voucher-relation';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

@Component({
    selector: 'jhi-purchase-order-voucher-relation-extended',
    templateUrl: './purchase-order-voucher-relation-extended.component.html'
})
export class PurchaseOrderVoucherRelationExtendedComponent extends PurchaseOrderVoucherRelationComponent implements OnInit, OnDestroy {
    @Input()
    purchaseOrder: IPurchaseOrder;
    purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation;

    constructor(
        protected purchaseOrderVoucherRelationService: PurchaseOrderVoucherRelationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected router: Router
    ) {
        super(purchaseOrderVoucherRelationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        this.purchaseOrderVoucherRelationService
            .query({
                'purchaseOrderId.equals': this.purchaseOrder.id,
                page: 0,
                size: 200,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IPurchaseOrderVoucherRelation[]>) => this.paginatePurchaseOrderVoucherRelations(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.purchaseOrderVoucherRelation = new PurchaseOrderVoucherRelation();
        this.purchaseOrderVoucherRelation.purchaseOrderId = this.purchaseOrder.id;
        this.registerChangeInPurchaseOrderVoucherRelations();
    }

    gotoVoucher(purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation) {
        if (purchaseOrderVoucherRelation.voucherNo.includes('JN'))
            this.router.navigate(['/journal-voucher/voucher-no', purchaseOrderVoucherRelation.voucherNo, 'edit']);
        else if (purchaseOrderVoucherRelation.voucherNo.includes('CV'))
            this.router.navigate(['/contra-voucher/voucher-no', purchaseOrderVoucherRelation.voucherNo, 'edit']);
        else if (purchaseOrderVoucherRelation.voucherNo.includes('BP'))
            this.router.navigate(['/payment-voucher/voucher-no', purchaseOrderVoucherRelation.voucherNo, 'edit']);
        else if (purchaseOrderVoucherRelation.voucherNo.includes('BR'))
            this.router.navigate(['/receipt-voucher/voucher-no', purchaseOrderVoucherRelation.voucherNo, 'edit']);
    }
}
