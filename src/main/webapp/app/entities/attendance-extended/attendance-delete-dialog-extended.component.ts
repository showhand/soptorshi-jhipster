import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { AttendanceDeleteDialogComponent, AttendanceDeletePopupComponent, AttendanceService } from 'app/entities/attendance';

@Component({
    selector: 'jhi-attendance-delete-dialog-extended',
    templateUrl: './attendance-delete-dialog-extended.component.html'
})
export class AttendanceDeleteDialogExtendedComponent extends AttendanceDeleteDialogComponent {
    constructor(
        protected attendanceService: AttendanceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(attendanceService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-attendance-delete-popup-extended',
    template: ''
})
export class AttendanceDeletePopupExtendedComponent extends AttendanceDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ attendance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AttendanceDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.attendance = attendance;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/attendance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/attendance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }
}
