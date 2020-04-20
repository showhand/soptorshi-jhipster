import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyZoneExtendedService } from './supply-zone-extended.service';
import { SupplyZoneDeleteDialogComponent, SupplyZoneDeletePopupComponent } from 'app/entities/supply-zone';

@Component({
    selector: 'jhi-supply-zone-delete-dialog-extended',
    templateUrl: './supply-zone-delete-dialog-extended.component.html'
})
export class SupplyZoneDeleteDialogExtendedComponent extends SupplyZoneDeleteDialogComponent {
    constructor(
        protected supplyZoneService: SupplyZoneExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyZoneService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-zone-delete-popup-extended',
    template: ''
})
export class SupplyZoneDeletePopupExtendedComponent extends SupplyZoneDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
