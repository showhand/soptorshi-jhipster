import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, purchaseOrderVoucherRelationService, voucherService, purchaseOrderService, activatedRoute);
    }
}
