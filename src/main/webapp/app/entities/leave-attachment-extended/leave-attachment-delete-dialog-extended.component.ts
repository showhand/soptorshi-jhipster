import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { LeaveAttachmentExtendedService } from './leave-attachment-extended.service';
import { LeaveAttachmentDeleteDialogComponent, LeaveAttachmentDeletePopupComponent } from 'app/entities/leave-attachment';

@Component({
    selector: 'jhi-leave-attachment-delete-dialog-extended',
    templateUrl: './leave-attachment-delete-dialog-extended.component.html'
})
export class LeaveAttachmentDeleteDialogExtendedComponent extends LeaveAttachmentDeleteDialogComponent {
    leaveAttachment: ILeaveAttachment;

    constructor(
        protected leaveAttachmentService: LeaveAttachmentExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(leaveAttachmentService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.leaveAttachmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'leaveAttachmentListModification',
                content: 'Deleted an leaveAttachment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-leave-attachment-delete-popup-extended',
    template: ''
})
export class LeaveAttachmentDeletePopupExtendedComponent extends LeaveAttachmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveAttachment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LeaveAttachmentDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.leaveAttachment = leaveAttachment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/leave-attachment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/leave-attachment', { outlets: { popup: null } }]);
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
