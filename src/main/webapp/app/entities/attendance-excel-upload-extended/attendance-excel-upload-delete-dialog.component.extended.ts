import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadServiceExtended } from './attendance-excel-upload.service.extended';
import {
    AttendanceExcelUploadDeleteDialogComponent,
    AttendanceExcelUploadDeletePopupComponent
} from 'app/entities/attendance-excel-upload';

@Component({
    selector: 'jhi-attendance-excel-upload-delete-dialog-extended',
    templateUrl: './attendance-excel-upload-delete-dialog.component.extended.html'
})
export class AttendanceExcelUploadDeleteDialogComponentExtended extends AttendanceExcelUploadDeleteDialogComponent {
    attendanceExcelUpload: IAttendanceExcelUpload;

    constructor(
        protected attendanceExcelUploadService: AttendanceExcelUploadServiceExtended,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(attendanceExcelUploadService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.attendanceExcelUploadService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'attendanceExcelUploadListModification',
                content: 'Deleted an attendanceExcelUpload'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-attendance-excel-upload-delete-popup-extended',
    template: ''
})
export class AttendanceExcelUploadDeletePopupComponentExtended extends AttendanceExcelUploadDeletePopupComponent
    implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ attendanceExcelUpload }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AttendanceExcelUploadDeleteDialogComponentExtended as Component, {
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

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
