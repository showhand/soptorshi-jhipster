import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';
import { CommercialPurchaseOrderItemService } from './commercial-purchase-order-item.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-purchase-order-item-update',
    templateUrl: './commercial-purchase-order-item-update.component.html'
})
export class CommercialPurchaseOrderItemUpdateComponent implements OnInit {
    commercialPurchaseOrderItem: ICommercialPurchaseOrderItem;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];
    createOnDp: any;
    updatedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPurchaseOrderItemService: CommercialPurchaseOrderItemService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrderItem }) => {
            this.commercialPurchaseOrderItem = commercialPurchaseOrderItem;
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
        if (this.commercialPurchaseOrderItem.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPurchaseOrderItemService.update(this.commercialPurchaseOrderItem));
        } else {
            this.subscribeToSaveResponse(this.commercialPurchaseOrderItemService.create(this.commercialPurchaseOrderItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPurchaseOrderItem>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialPurchaseOrderItem>) => this.onSaveSuccess(),
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
