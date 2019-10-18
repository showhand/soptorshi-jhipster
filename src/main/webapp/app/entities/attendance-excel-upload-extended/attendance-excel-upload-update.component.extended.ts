import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadServiceExtended } from './attendance-excel-upload.service.extended';
import { AttendanceExcelUploadUpdateComponent } from 'app/entities/attendance-excel-upload';

@Component({
    selector: 'jhi-attendance-excel-upload-update-extended',
    templateUrl: './attendance-excel-upload-update.component.extended.html'
})
export class AttendanceExcelUploadUpdateComponentExtended extends AttendanceExcelUploadUpdateComponent implements OnInit {
    attendanceExcelUpload: IAttendanceExcelUpload;
    isSaving: boolean;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected attendanceExcelUploadService: AttendanceExcelUploadServiceExtended,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, attendanceExcelUploadService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ attendanceExcelUpload }) => {
            this.attendanceExcelUpload = attendanceExcelUpload;
        });
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
        if (this.attendanceExcelUpload.id !== undefined) {
            this.subscribeToSaveResponse(this.attendanceExcelUploadService.update(this.attendanceExcelUpload));
        } else {
            this.subscribeToSaveResponse(this.attendanceExcelUploadService.create(this.attendanceExcelUpload));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendanceExcelUpload>>) {
        result.subscribe(
            (res: HttpResponse<IAttendanceExcelUpload>) => this.onSaveSuccess(),
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
}
