import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { LeaveAttachmentExtendedService } from './leave-attachment-extended.service';
import { LeaveAttachmentDeleteDialogComponent, LeaveAttachmentDeletePopupComponent } from 'app/entities/leave-attachment';

@Component({
    selector: 'jhi-leave-attachment-delete-dialog-extended',
    templateUrl: './leave-attachment-delete-dialog-extended.component.html'
})
export class LeaveAttachmentDeleteDialogExtendedComponent extends LeaveAttachmentDeleteDialogComponent {
    constructor(
        protected leaveAttachmentService: LeaveAttachmentExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(leaveAttachmentService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-leave-attachment-delete-popup-extended',
    template: ''
})
export class LeaveAttachmentDeletePopupExtendedComponent extends LeaveAttachmentDeletePopupComponent implements OnInit {
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
}
