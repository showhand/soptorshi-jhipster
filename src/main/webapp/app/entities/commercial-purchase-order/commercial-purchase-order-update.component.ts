import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from './commercial-purchase-order.service';

@Component({
    selector: 'jhi-commercial-purchase-order-update',
    templateUrl: './commercial-purchase-order-update.component.html'
})
export class CommercialPurchaseOrderUpdateComponent implements OnInit {
    commercialPurchaseOrder: ICommercialPurchaseOrder;
    isSaving: boolean;
    purchaseOrderDateDp: any;
    shipmentDateDp: any;
    createOnDp: any;

    constructor(protected commercialPurchaseOrderService: CommercialPurchaseOrderService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrder }) => {
            this.commercialPurchaseOrder = commercialPurchaseOrder;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialPurchaseOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPurchaseOrderService.update(this.commercialPurchaseOrder));
        } else {
            this.subscribeToSaveResponse(this.commercialPurchaseOrderService.create(this.commercialPurchaseOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPurchaseOrder>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialPurchaseOrder>) => this.onSaveSuccess(),
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
}
