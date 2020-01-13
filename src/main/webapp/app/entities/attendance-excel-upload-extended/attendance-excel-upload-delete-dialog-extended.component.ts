import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { AttendanceExcelUploadExtendedService } from './attendance-excel-upload-extended.service';
import {
    AttendanceExcelUploadDeleteDialogComponent,
    AttendanceExcelUploadDeletePopupComponent
} from 'app/entities/attendance-excel-upload';

@Component({
    selector: 'jhi-attendance-excel-upload-delete-dialog-extended',
    templateUrl: './attendance-excel-upload-delete-dialog-extended.component.html'
})
export class AttendanceExcelUploadDeleteDialogExtendedComponent extends AttendanceExcelUploadDeleteDialogComponent {
    constructor(
        protected attendanceExcelUploadService: AttendanceExcelUploadExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(attendanceExcelUploadService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-attendance-excel-upload-delete-popup-extended',
    template: ''
})
export class AttendanceExcelUploadDeletePopupExtendedComponent extends AttendanceExcelUploadDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
