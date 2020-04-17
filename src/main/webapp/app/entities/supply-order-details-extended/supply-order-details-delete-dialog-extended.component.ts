import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyOrderDetailsExtendedService } from './supply-order-details-extended.service';
import { SupplyOrderDetailsDeleteDialogComponent, SupplyOrderDetailsDeletePopupComponent } from 'app/entities/supply-order-details';

@Component({
    selector: 'jhi-supply-order-details-delete-dialog-extended',
    templateUrl: './supply-order-details-delete-dialog-extended.component.html'
})
export class SupplyOrderDetailsDeleteDialogExtendedComponent extends SupplyOrderDetailsDeleteDialogComponent {
    constructor(
        protected supplyOrderDetailsService: SupplyOrderDetailsExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyOrderDetailsService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-order-details-delete-popup-extended',
    template: ''
})
export class SupplyOrderDetailsDeletePopupExtendedComponent extends SupplyOrderDetailsDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
