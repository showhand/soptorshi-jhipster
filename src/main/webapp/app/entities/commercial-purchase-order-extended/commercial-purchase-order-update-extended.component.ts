import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderExtendedService } from './commercial-purchase-order-extended.service';
import { CommercialPurchaseOrderUpdateComponent } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-purchase-order-update-extended',
    templateUrl: './commercial-purchase-order-update-extended.component.html'
})
export class CommercialPurchaseOrderUpdateExtendedComponent extends CommercialPurchaseOrderUpdateComponent {
    commercialPurchaseOrder: ICommercialPurchaseOrder;
    isSaving: boolean;
    purchaseOrderDateDp: any;
    shipmentDateDp: any;
    createOnDp: any;
    updatedOnDp: any;

    constructor(
        protected commercialPurchaseOrderService: CommercialPurchaseOrderExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(commercialPurchaseOrderService, activatedRoute);
    }

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
