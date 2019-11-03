import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingService } from './commercial-packaging.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-packaging-update',
    templateUrl: './commercial-packaging-update.component.html'
})
export class CommercialPackagingUpdateComponent implements OnInit {
    commercialPackaging: ICommercialPackaging;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];
    consignmentDateDp: any;
    createOnDp: any;
    updatedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPackagingService: CommercialPackagingService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPackaging }) => {
            this.commercialPackaging = commercialPackaging;
        });
        this.commercialPurchaseOrderService
            .query({ 'commercialPackagingId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPurchaseOrder[]) => {
                    if (!this.commercialPackaging.commercialPurchaseOrderId) {
                        this.commercialpurchaseorders = res;
                    } else {
                        this.commercialPurchaseOrderService
                            .find(this.commercialPackaging.commercialPurchaseOrderId)
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
        if (this.commercialPackaging.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPackagingService.update(this.commercialPackaging));
        } else {
            this.subscribeToSaveResponse(this.commercialPackagingService.create(this.commercialPackaging));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPackaging>>) {
        result.subscribe((res: HttpResponse<ICommercialPackaging>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
