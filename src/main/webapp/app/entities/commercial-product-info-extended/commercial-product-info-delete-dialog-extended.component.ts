import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommercialProductInfoExtendedService } from './commercial-product-info-extended.service';
import {
    CommercialProductInfoDeleteDialogComponent,
    CommercialProductInfoDeletePopupComponent
} from 'app/entities/commercial-product-info';

@Component({
    selector: 'jhi-commercial-product-info-delete-dialog-extended',
    templateUrl: './commercial-product-info-delete-dialog-extended.component.html'
})
export class CommercialProductInfoDeleteDialogExtendedComponent extends CommercialProductInfoDeleteDialogComponent {
    constructor(
        protected commercialProductInfoService: CommercialProductInfoExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialProductInfoService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-commercial-product-info-delete-popup-extended',
    template: ''
})
export class CommercialProductInfoDeletePopupExtendedComponent extends CommercialProductInfoDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
