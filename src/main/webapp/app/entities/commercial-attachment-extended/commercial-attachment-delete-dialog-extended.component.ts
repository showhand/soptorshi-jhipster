import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentExtendedService } from './commercial-attachment-extended.service';
import { CommercialAttachmentDeleteDialogComponent, CommercialAttachmentDeletePopupComponent } from 'app/entities/commercial-attachment';

@Component({
    selector: 'jhi-commercial-attachment-delete-dialog-extended',
    templateUrl: './commercial-attachment-delete-dialog-extended.component.html'
})
export class CommercialAttachmentDeleteDialogExtendedComponent extends CommercialAttachmentDeleteDialogComponent {
    commercialAttachment: ICommercialAttachment;

    constructor(
        protected commercialAttachmentService: CommercialAttachmentExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialAttachmentService, activeModal, eventManager);
    }

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
export class CommercialAttachmentDeletePopupExtendedComponent extends CommercialAttachmentDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialAttachment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialAttachmentDeleteDialogExtendedComponent as Component, {
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
