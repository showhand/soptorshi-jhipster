import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusService } from './commercial-po-status.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-po-status-update',
    templateUrl: './commercial-po-status-update.component.html'
})
export class CommercialPoStatusUpdateComponent implements OnInit {
    commercialPoStatus: ICommercialPoStatus;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];
    createOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPoStatusService: CommercialPoStatusService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPoStatus }) => {
            this.commercialPoStatus = commercialPoStatus;
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
        if (this.commercialPoStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPoStatusService.update(this.commercialPoStatus));
        } else {
            this.subscribeToSaveResponse(this.commercialPoStatusService.create(this.commercialPoStatus));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPoStatus>>) {
        result.subscribe((res: HttpResponse<ICommercialPoStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
