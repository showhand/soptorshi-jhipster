import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyZoneManagerExtendedService } from './supply-zone-manager-extended.service';
import { SupplyZoneManagerDeleteDialogComponent, SupplyZoneManagerDeletePopupComponent } from 'app/entities/supply-zone-manager';

@Component({
    selector: 'jhi-supply-zone-manager-delete-dialog-extended',
    templateUrl: './supply-zone-manager-delete-dialog-extended.component.html'
})
export class SupplyZoneManagerDeleteDialogExtendedComponent extends SupplyZoneManagerDeleteDialogComponent {
    constructor(
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyZoneManagerService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-zone-manager-delete-popup-extended',
    template: ''
})
export class SupplyZoneManagerDeletePopupExtendedComponent extends SupplyZoneManagerDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
