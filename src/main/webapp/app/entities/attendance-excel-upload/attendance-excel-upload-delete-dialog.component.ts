import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadService } from './attendance-excel-upload.service';

@Component({
    selector: 'jhi-attendance-excel-upload-delete-dialog',
    templateUrl: './attendance-excel-upload-delete-dialog.component.html'
})
export class AttendanceExcelUploadDeleteDialogComponent {
    attendanceExcelUpload: IAttendanceExcelUpload;

    constructor(
        protected attendanceExcelUploadService: AttendanceExcelUploadService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-attendance-excel-upload-delete-popup',
    template: ''
})
export class AttendanceExcelUploadDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ attendanceExcelUpload }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AttendanceExcelUploadDeleteDialogComponent as Component, {
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
