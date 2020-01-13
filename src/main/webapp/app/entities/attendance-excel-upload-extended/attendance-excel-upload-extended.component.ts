import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { AttendanceExcelUploadExtendedService } from './attendance-excel-upload-extended.service';
import { AttendanceExcelUploadComponent } from 'app/entities/attendance-excel-upload';

@Component({
    selector: 'jhi-attendance-excel-upload-extended',
    templateUrl: './attendance-excel-upload-extended.component.html'
})
export class AttendanceExcelUploadExtendedComponent extends AttendanceExcelUploadComponent {
    constructor(
        protected attendanceExcelUploadServiceExtended: AttendanceExcelUploadExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(attendanceExcelUploadServiceExtended, jhiAlertService, dataUtils, eventManager, parseLinks, activatedRoute, accountService);
    }
}
