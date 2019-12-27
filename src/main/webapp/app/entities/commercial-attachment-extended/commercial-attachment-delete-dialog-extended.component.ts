import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
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
}

@Component({
    selector: 'jhi-commercial-attachment-delete-popup-extended',
    template: ''
})
export class CommercialAttachmentDeletePopupExtendedComponent extends CommercialAttachmentDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
