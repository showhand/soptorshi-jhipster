import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IWorkOrder } from 'app/shared/model/work-order.model';
import { WorkOrderService } from './work-order.service';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';

@Component({
    selector: 'jhi-work-order-update',
    templateUrl: './work-order-update.component.html'
})
export class WorkOrderUpdateComponent implements OnInit {
    workOrder: IWorkOrder;
    isSaving: boolean;

    requisitions: IRequisition[];
    issueDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected workOrderService: WorkOrderService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ workOrder }) => {
            this.workOrder = workOrder;
        });
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.workOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.workOrderService.update(this.workOrder));
        } else {
            this.subscribeToSaveResponse(this.workOrderService.create(this.workOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkOrder>>) {
        result.subscribe((res: HttpResponse<IWorkOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }
}
