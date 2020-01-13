import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { AttendanceExcelUploadDetailComponent } from 'app/entities/attendance-excel-upload';

@Component({
    selector: 'jhi-attendance-excel-upload-detail-extended',
    templateUrl: './attendance-excel-upload-detail-extended.component.html'
})
export class AttendanceExcelUploadDetailExtendedComponent extends AttendanceExcelUploadDetailComponent {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
