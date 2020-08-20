import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { AttendanceExcelUploadExtendedService } from './attendance-excel-upload-extended.service';
import { AttendanceExcelUploadUpdateComponent } from 'app/entities/attendance-excel-upload';

@Component({
    selector: 'jhi-attendance-excel-upload-update-extended',
    templateUrl: './attendance-excel-upload-update-extended.component.html'
})
export class AttendanceExcelUploadUpdateExtendedComponent extends AttendanceExcelUploadUpdateComponent {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected attendanceExcelUploadService: AttendanceExcelUploadExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {
        super(dataUtils, attendanceExcelUploadService, activatedRoute);
    }

    previousState() {
        this.router.navigate(['/attendance']);
    }
}
