import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommercialPiExtendedService } from './commercial-pi-extended.service';
import { CommercialPiDeleteDialogComponent, CommercialPiDeletePopupComponent } from 'app/entities/commercial-pi';

@Component({
    selector: 'jhi-commercial-pi-delete-dialog-extended',
    templateUrl: './commercial-pi-delete-dialog-extended.component.html'
})
export class CommercialPiDeleteDialogExtendedComponent extends CommercialPiDeleteDialogComponent {
    constructor(
        protected commercialPiService: CommercialPiExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPiService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-commercial-pi-delete-popup-extended',
    template: ''
})
export class CommercialPiDeletePopupExtendedComponent extends CommercialPiDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
