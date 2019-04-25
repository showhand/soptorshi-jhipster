import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';
import { AcademicInformationAttachmentService } from './academic-information-attachment.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-academic-information-attachment-update',
    templateUrl: './academic-information-attachment-update.component.html'
})
export class AcademicInformationAttachmentUpdateComponent implements OnInit {
    academicInformationAttachment: IAcademicInformationAttachment;
    isSaving: boolean;

    employees: IEmployee[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected academicInformationAttachmentService: AcademicInformationAttachmentService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ academicInformationAttachment }) => {
            this.academicInformationAttachment = academicInformationAttachment;
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
        if (this.academicInformationAttachment.id !== undefined) {
            this.subscribeToSaveResponse(this.academicInformationAttachmentService.update(this.academicInformationAttachment));
        } else {
            this.subscribeToSaveResponse(this.academicInformationAttachmentService.create(this.academicInformationAttachment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcademicInformationAttachment>>) {
        result.subscribe(
            (res: HttpResponse<IAcademicInformationAttachment>) => this.onSaveSuccess(),
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
