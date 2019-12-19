import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoService } from './commercial-payment-info.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-payment-info-update',
    templateUrl: './commercial-payment-info-update.component.html'
})
export class CommercialPaymentInfoUpdateComponent implements OnInit {
    commercialPaymentInfo: ICommercialPaymentInfo;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];
    createdOnDp: any;
    updatedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPaymentInfoService: CommercialPaymentInfoService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPaymentInfo }) => {
            this.commercialPaymentInfo = commercialPaymentInfo;
        });
        this.commercialPurchaseOrderService
            .query({ 'commercialPaymentInfoId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPurchaseOrder[]) => {
                    if (!this.commercialPaymentInfo.commercialPurchaseOrderId) {
                        this.commercialpurchaseorders = res;
                    } else {
                        this.commercialPurchaseOrderService
                            .find(this.commercialPaymentInfo.commercialPurchaseOrderId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialPurchaseOrder>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialPurchaseOrder>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialPurchaseOrder) => (this.commercialpurchaseorders = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialPaymentInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPaymentInfoService.update(this.commercialPaymentInfo));
        } else {
            this.subscribeToSaveResponse(this.commercialPaymentInfoService.create(this.commercialPaymentInfo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPaymentInfo>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialPaymentInfo>) => this.onSaveSuccess(),
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

    trackCommercialPurchaseOrderById(index: number, item: ICommercialPurchaseOrder) {
        return item.id;
    }
}
