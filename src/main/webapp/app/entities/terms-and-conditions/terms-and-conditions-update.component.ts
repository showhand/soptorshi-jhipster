import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsService } from './terms-and-conditions.service';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';

@Component({
    selector: 'jhi-terms-and-conditions-update',
    templateUrl: './terms-and-conditions-update.component.html'
})
export class TermsAndConditionsUpdateComponent implements OnInit {
    termsAndConditions: ITermsAndConditions;
    isSaving: boolean;

    purchaseorders: IPurchaseOrder[];
    modifiedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected termsAndConditionsService: TermsAndConditionsService,
        protected purchaseOrderService: PurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ termsAndConditions }) => {
            this.termsAndConditions = termsAndConditions;
        });
        this.purchaseOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPurchaseOrder[]>) => response.body)
            )
            .subscribe((res: IPurchaseOrder[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.termsAndConditions.id !== undefined) {
            this.subscribeToSaveResponse(this.termsAndConditionsService.update(this.termsAndConditions));
        } else {
            this.subscribeToSaveResponse(this.termsAndConditionsService.create(this.termsAndConditions));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITermsAndConditions>>) {
        result.subscribe((res: HttpResponse<ITermsAndConditions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPurchaseOrderById(index: number, item: IPurchaseOrder) {
        return item.id;
    }
}
