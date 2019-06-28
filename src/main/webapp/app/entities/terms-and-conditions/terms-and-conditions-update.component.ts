import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsService } from './terms-and-conditions.service';
import { IWorkOrder } from 'app/shared/model/work-order.model';
import { WorkOrderService } from 'app/entities/work-order';

@Component({
    selector: 'jhi-terms-and-conditions-update',
    templateUrl: './terms-and-conditions-update.component.html'
})
export class TermsAndConditionsUpdateComponent implements OnInit {
    termsAndConditions: ITermsAndConditions;
    isSaving: boolean;

    workorders: IWorkOrder[];
    modifiedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected termsAndConditionsService: TermsAndConditionsService,
        protected workOrderService: WorkOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ termsAndConditions }) => {
            this.termsAndConditions = termsAndConditions;
        });
        this.workOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWorkOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWorkOrder[]>) => response.body)
            )
            .subscribe((res: IWorkOrder[]) => (this.workorders = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackWorkOrderById(index: number, item: IWorkOrder) {
        return item.id;
    }
}
