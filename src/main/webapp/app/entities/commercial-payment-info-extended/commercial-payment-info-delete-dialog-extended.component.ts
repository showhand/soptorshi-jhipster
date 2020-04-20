import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { CommercialPaymentInfoExtendedService } from './commercial-payment-info-extended.service';
import {
    CommercialPaymentInfoDeleteDialogComponent,
    CommercialPaymentInfoDeletePopupComponent
} from 'app/entities/commercial-payment-info';

@Component({
    selector: 'jhi-commercial-payment-info-delete-dialog-extended',
    templateUrl: './commercial-payment-info-delete-dialog-extended.component.html'
})
export class CommercialPaymentInfoDeleteDialogExtendedComponent extends CommercialPaymentInfoDeleteDialogComponent {
    constructor(
        protected commercialPaymentInfoService: CommercialPaymentInfoExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPaymentInfoService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-commercial-payment-info-delete-popup-extended',
    template: ''
})
export class CommercialPaymentInfoDeletePopupExtendedComponent extends CommercialPaymentInfoDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
