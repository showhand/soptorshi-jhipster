import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationService } from './purchase-order-voucher-relation.service';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';

@Component({
    selector: 'jhi-purchase-order-voucher-relation-update',
    templateUrl: './purchase-order-voucher-relation-update.component.html'
})
export class PurchaseOrderVoucherRelationUpdateComponent implements OnInit {
    purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation;
    isSaving: boolean;

    vouchers: IVoucher[];

    purchaseorders: IPurchaseOrder[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderVoucherRelationService: PurchaseOrderVoucherRelationService,
        protected voucherService: VoucherService,
        protected purchaseOrderService: PurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseOrderVoucherRelation }) => {
            this.purchaseOrderVoucherRelation = purchaseOrderVoucherRelation;
        });
        this.voucherService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVoucher[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVoucher[]>) => response.body)
            )
            .subscribe((res: IVoucher[]) => (this.vouchers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.purchaseOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPurchaseOrder[]>) => response.body)
            )
            .subscribe((res: IPurchaseOrder[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrderVoucherRelation.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseOrderVoucherRelationService.update(this.purchaseOrderVoucherRelation));
        } else {
            this.subscribeToSaveResponse(this.purchaseOrderVoucherRelationService.create(this.purchaseOrderVoucherRelation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrderVoucherRelation>>) {
        result.subscribe(
            (res: HttpResponse<IPurchaseOrderVoucherRelation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackVoucherById(index: number, item: IVoucher) {
        return item.id;
    }

    trackPurchaseOrderById(index: number, item: IPurchaseOrder) {
        return item.id;
    }
}
