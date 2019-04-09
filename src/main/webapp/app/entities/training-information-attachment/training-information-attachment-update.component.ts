import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';
import { TrainingInformationAttachmentService } from './training-information-attachment.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-training-information-attachment-update',
    templateUrl: './training-information-attachment-update.component.html'
})
export class TrainingInformationAttachmentUpdateComponent implements OnInit {
    trainingInformationAttachment: ITrainingInformationAttachment;
    isSaving: boolean;

    employees: IEmployee[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected trainingInformationAttachmentService: TrainingInformationAttachmentService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ trainingInformationAttachment }) => {
            this.trainingInformationAttachment = trainingInformationAttachment;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.trainingInformationAttachment.id !== undefined) {
            this.subscribeToSaveResponse(this.trainingInformationAttachmentService.update(this.trainingInformationAttachment));
        } else {
            this.subscribeToSaveResponse(this.trainingInformationAttachmentService.create(this.trainingInformationAttachment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingInformationAttachment>>) {
        result.subscribe(
            (res: HttpResponse<ITrainingInformationAttachment>) => this.onSaveSuccess(),
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

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
}
