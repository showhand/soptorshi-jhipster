import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { LeaveAttachmentService } from './leave-attachment.service';

@Component({
    selector: 'jhi-leave-attachment-delete-dialog',
    templateUrl: './leave-attachment-delete-dialog.component.html'
})
export class LeaveAttachmentDeleteDialogComponent {
    leaveAttachment: ILeaveAttachment;

    constructor(
        protected leaveAttachmentService: LeaveAttachmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-leave-attachment-delete-popup',
    template: ''
})
export class LeaveAttachmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveAttachment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LeaveAttachmentDeleteDialogComponent as Component, {
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
