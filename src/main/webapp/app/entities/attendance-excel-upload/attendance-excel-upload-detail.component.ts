import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';

@Component({
    selector: 'jhi-attendance-excel-upload-detail',
    templateUrl: './attendance-excel-upload-detail.component.html'
})
export class AttendanceExcelUploadDetailComponent implements OnInit {
    attendanceExcelUpload: IAttendanceExcelUpload;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
