import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';
import { ExperienceInformationAttachmentService } from './experience-information-attachment.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-experience-information-attachment-update',
    templateUrl: './experience-information-attachment-update.component.html'
})
export class ExperienceInformationAttachmentUpdateComponent implements OnInit {
    @Input()
    experienceInformationAttachment: IExperienceInformationAttachment;
    @Output()
    showExperienceInformationAttachmentSection: EventEmitter<any> = new EventEmitter();

    isSaving: boolean;

    employees: IEmployee[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected experienceInformationAttachmentService: ExperienceInformationAttachmentService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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
        this.showExperienceInformationAttachmentSection.emit();
    }

    save() {
        this.isSaving = true;
        if (this.experienceInformationAttachment.id !== undefined) {
            this.subscribeToSaveResponse(this.experienceInformationAttachmentService.update(this.experienceInformationAttachment));
        } else {
            this.subscribeToSaveResponse(this.experienceInformationAttachmentService.create(this.experienceInformationAttachment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperienceInformationAttachment>>) {
        result.subscribe(
            (res: HttpResponse<IExperienceInformationAttachment>) => this.onSaveSuccess(),
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
