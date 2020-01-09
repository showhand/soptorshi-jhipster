import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from './purchase-order.service';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';

@Component({
    selector: 'jhi-purchase-order-update',
    templateUrl: './purchase-order-update.component.html'
})
export class PurchaseOrderUpdateComponent implements OnInit {
    purchaseOrder: IPurchaseOrder;
    isSaving: boolean;

    requisitions: IRequisition[];

    quotations: IQuotation[];
    issueDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderService: PurchaseOrderService,
        protected requisitionService: RequisitionService,
        protected quotationService: QuotationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseOrder }) => {
            this.purchaseOrder = purchaseOrder;
        });
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.quotationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQuotation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQuotation[]>) => response.body)
            )
            .subscribe((res: IQuotation[]) => (this.quotations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseOrderService.update(this.purchaseOrder));
        } else {
            this.subscribeToSaveResponse(this.purchaseOrderService.create(this.purchaseOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrder>>) {
        result.subscribe(
            (res: HttpResponse<IPurchaseOrder>) => {
                this.onSaveSuccess();
                this.purchaseOrder = res.body;
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }

    trackQuotationById(index: number, item: IQuotation) {
        return item.id;
    }
}
