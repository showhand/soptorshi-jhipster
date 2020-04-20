import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyAreaManagerExtendedService } from './supply-area-manager-extended.service';
import { SupplyAreaManagerDeleteDialogComponent, SupplyAreaManagerDeletePopupComponent } from 'app/entities/supply-area-manager';

@Component({
    selector: 'jhi-supply-area-manager-delete-dialog-extended',
    templateUrl: './supply-area-manager-delete-dialog-extended.component.html'
})
export class SupplyAreaManagerDeleteDialogExtendedComponent extends SupplyAreaManagerDeleteDialogComponent {
    constructor(
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyAreaManagerService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-area-manager-delete-popup-extended',
    template: ''
})
export class SupplyAreaManagerDeletePopupExtendedComponent extends SupplyAreaManagerDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
