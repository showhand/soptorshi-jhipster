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

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ attendanceExcelUpload }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AttendanceExcelUploadDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.attendanceExcelUpload = attendanceExcelUpload;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/attendance-excel-upload', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/attendance-excel-upload', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }
}
