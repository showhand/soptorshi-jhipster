import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommercialPoExtendedService } from './commercial-po-extended.service';
import { CommercialPoDeleteDialogComponent, CommercialPoDeletePopupComponent } from 'app/entities/commercial-po';

@Component({
    selector: 'jhi-commercial-po-delete-dialog-extended',
    templateUrl: './commercial-po-delete-dialog-extended.component.html'
})
export class CommercialPoDeleteDialogExtendedComponent extends CommercialPoDeleteDialogComponent {
    constructor(
        protected commercialPoService: CommercialPoExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPoService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-commercial-po-delete-popup-extended',
    template: ''
})
export class CommercialPoDeletePopupExtendedComponent extends CommercialPoDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
