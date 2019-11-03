import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderService } from './commercial-work-order.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-work-order-update',
    templateUrl: './commercial-work-order-update.component.html'
})
export class CommercialWorkOrderUpdateComponent implements OnInit {
    commercialWorkOrder: ICommercialWorkOrder;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];
    workOrderDateDp: any;
    deliveryDateDp: any;
    createOnDp: any;
    updatedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialWorkOrderService: CommercialWorkOrderService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialWorkOrder }) => {
            this.commercialWorkOrder = commercialWorkOrder;
        });
        this.commercialPurchaseOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPurchaseOrder[]) => (this.commercialpurchaseorders = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialWorkOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialWorkOrderService.update(this.commercialWorkOrder));
        } else {
            this.subscribeToSaveResponse(this.commercialWorkOrderService.create(this.commercialWorkOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialWorkOrder>>) {
        result.subscribe((res: HttpResponse<ICommercialWorkOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
