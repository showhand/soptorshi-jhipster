import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { CommercialWorkOrderDetailsExtendedService } from './commercial-work-order-details-extended.service';
import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderService } from 'app/entities/commercial-work-order';
import { CommercialWorkOrderDetailsUpdateComponent } from 'app/entities/commercial-work-order-details';

@Component({
    selector: 'jhi-commercial-work-order-details-update-extended',
    templateUrl: './commercial-work-order-details-update-extended.component.html'
})
export class CommercialWorkOrderDetailsUpdateExtendedComponent extends CommercialWorkOrderDetailsUpdateComponent {
    commercialWorkOrderDetails: ICommercialWorkOrderDetails;
    isSaving: boolean;

    commercialworkorders: ICommercialWorkOrder[];
    createOnDp: any;
    updatedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialWorkOrderDetailsService: CommercialWorkOrderDetailsExtendedService,
        protected commercialWorkOrderService: CommercialWorkOrderService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, commercialWorkOrderDetailsService, commercialWorkOrderService, activatedRoute);
    }

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
