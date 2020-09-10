import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';

@Component({
    selector: 'jhi-leave-application-delete-dialog',
    templateUrl: './leave-application-delete-dialog.component.html'
})
export class LeaveApplicationDeleteDialogComponent {
    leaveApplication: ILeaveApplication;

    constructor(
        protected leaveApplicationService: LeaveApplicationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.leaveApplicationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'leaveApplicationListModification',
                content: 'Deleted an leaveApplication'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-leave-application-delete-popup',
    template: ''
})
export class LeaveApplicationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveApplication }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LeaveApplicationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.leaveApplication = leaveApplication;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/leave-application', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/leave-application', { outlets: { popup: null } }]);
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
