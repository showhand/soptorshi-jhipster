import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentService } from './commercial-attachment.service';

@Component({
    selector: 'jhi-commercial-attachment-delete-dialog',
    templateUrl: './commercial-attachment-delete-dialog.component.html'
})
export class CommercialAttachmentDeleteDialogComponent {
    commercialAttachment: ICommercialAttachment;

    constructor(
        protected commercialAttachmentService: CommercialAttachmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialAttachmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialAttachmentListModification',
                content: 'Deleted an commercialAttachment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-attachment-delete-popup',
    template: ''
})
export class CommercialAttachmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialAttachment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialAttachmentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialAttachment = commercialAttachment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-attachment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-attachment', { outlets: { popup: null } }]);
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
