import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAttachment } from 'app/shared/model/attachment.model';
import { AttachmentService } from './attachment.service';
import { IAcademicInformation } from 'app/shared/model/academic-information.model';
import { AcademicInformationService } from 'app/entities/academic-information';
import { ITrainingInformation } from 'app/shared/model/training-information.model';
import { TrainingInformationService } from 'app/entities/training-information';
import { IExperienceInformation } from 'app/shared/model/experience-information.model';
import { ExperienceInformationService } from 'app/entities/experience-information';

@Component({
    selector: 'jhi-attachment-update',
    templateUrl: './attachment-update.component.html'
})
export class AttachmentUpdateComponent implements OnInit {
    attachment: IAttachment;
    isSaving: boolean;

    academicinformations: IAcademicInformation[];

    traininginformations: ITrainingInformation[];

    experienceinformations: IExperienceInformation[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected attachmentService: AttachmentService,
        protected academicInformationService: AcademicInformationService,
        protected trainingInformationService: TrainingInformationService,
        protected experienceInformationService: ExperienceInformationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ attachment }) => {
            this.attachment = attachment;
        });
        this.academicInformationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAcademicInformation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAcademicInformation[]>) => response.body)
            )
            .subscribe(
                (res: IAcademicInformation[]) => (this.academicinformations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.trainingInformationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITrainingInformation[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITrainingInformation[]>) => response.body)
            )
            .subscribe(
                (res: ITrainingInformation[]) => (this.traininginformations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.experienceInformationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExperienceInformation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExperienceInformation[]>) => response.body)
            )
            .subscribe(
                (res: IExperienceInformation[]) => (this.experienceinformations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        if (this.attachment.id !== undefined) {
            this.subscribeToSaveResponse(this.attachmentService.update(this.attachment));
        } else {
            this.subscribeToSaveResponse(this.attachmentService.create(this.attachment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachment>>) {
        result.subscribe((res: HttpResponse<IAttachment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAcademicInformationById(index: number, item: IAcademicInformation) {
        return item.id;
    }

    trackTrainingInformationById(index: number, item: ITrainingInformation) {
        return item.id;
    }

    trackExperienceInformationById(index: number, item: IExperienceInformation) {
        return item.id;
    }
}
