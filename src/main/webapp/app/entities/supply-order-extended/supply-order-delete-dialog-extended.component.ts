import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyOrderExtendedService } from './supply-order-extended.service';
import { SupplyOrderDeleteDialogComponent, SupplyOrderDeletePopupComponent } from 'app/entities/supply-order';

@Component({
    selector: 'jhi-supply-order-delete-dialog-extended',
    templateUrl: './supply-order-delete-dialog-extended.component.html'
})
export class SupplyOrderDeleteDialogExtendedComponent extends SupplyOrderDeleteDialogComponent {
    constructor(
        protected supplyOrderService: SupplyOrderExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyOrderService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-order-delete-popup-extended',
    template: ''
})
export class SupplyOrderDeletePopupExtendedComponent extends SupplyOrderDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
