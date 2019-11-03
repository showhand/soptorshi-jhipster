import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { CommercialWorkOrderDetailsService } from './commercial-work-order-details.service';
import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderService } from 'app/entities/commercial-work-order';

@Component({
    selector: 'jhi-commercial-work-order-details-update',
    templateUrl: './commercial-work-order-details-update.component.html'
})
export class CommercialWorkOrderDetailsUpdateComponent implements OnInit {
    commercialWorkOrderDetails: ICommercialWorkOrderDetails;
    isSaving: boolean;

    commercialworkorders: ICommercialWorkOrder[];
    createOnDp: any;
    updatedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialWorkOrderDetailsService: CommercialWorkOrderDetailsService,
        protected commercialWorkOrderService: CommercialWorkOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialWorkOrderDetails }) => {
            this.commercialWorkOrderDetails = commercialWorkOrderDetails;
        });
        this.commercialWorkOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialWorkOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialWorkOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialWorkOrder[]) => (this.commercialworkorders = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialWorkOrderDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialWorkOrderDetailsService.update(this.commercialWorkOrderDetails));
        } else {
            this.subscribeToSaveResponse(this.commercialWorkOrderDetailsService.create(this.commercialWorkOrderDetails));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialWorkOrderDetails>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialWorkOrderDetails>) => this.onSaveSuccess(),
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

    trackCommercialWorkOrderById(index: number, item: ICommercialWorkOrder) {
        return item.id;
    }
}
