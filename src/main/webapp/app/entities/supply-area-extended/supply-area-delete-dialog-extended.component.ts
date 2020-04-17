import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyAreaExtendedService } from './supply-area-extended.service';
import { SupplyAreaDeleteDialogComponent, SupplyAreaDeletePopupComponent } from 'app/entities/supply-area';

@Component({
    selector: 'jhi-supply-area-delete-dialog-extended',
    templateUrl: './supply-area-delete-dialog-extended.component.html'
})
export class SupplyAreaDeleteDialogExtendedComponent extends SupplyAreaDeleteDialogComponent {
    constructor(
        protected supplyAreaService: SupplyAreaExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyAreaService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-area-delete-popup-extended',
    template: ''
})
export class SupplyAreaDeletePopupExtendedComponent extends SupplyAreaDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
