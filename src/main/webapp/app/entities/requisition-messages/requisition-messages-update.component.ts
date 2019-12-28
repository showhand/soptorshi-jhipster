import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesService } from './requisition-messages.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';

@Component({
    selector: 'jhi-requisition-messages-update',
    templateUrl: './requisition-messages-update.component.html'
})
export class RequisitionMessagesUpdateComponent implements OnInit {
    requisitionMessages: IRequisitionMessages;
    isSaving: boolean;

    employees: IEmployee[];

    requisitions: IRequisition[];
    commentedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected requisitionMessagesService: RequisitionMessagesService,
        protected employeeService: EmployeeService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requisitionMessages }) => {
            this.requisitionMessages = requisitionMessages;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.requisitionMessages.id !== undefined) {
            this.subscribeToSaveResponse(this.requisitionMessagesService.update(this.requisitionMessages));
        } else {
            this.subscribeToSaveResponse(this.requisitionMessagesService.create(this.requisitionMessages));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisitionMessages>>) {
        result.subscribe((res: HttpResponse<IRequisitionMessages>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }
}
