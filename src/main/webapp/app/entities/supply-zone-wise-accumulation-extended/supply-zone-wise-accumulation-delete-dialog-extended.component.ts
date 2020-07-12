import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyZoneWiseAccumulationExtendedService } from './supply-zone-wise-accumulation-extended.service';
import {
    SupplyZoneWiseAccumulationDeleteDialogComponent,
    SupplyZoneWiseAccumulationDeletePopupComponent
} from 'app/entities/supply-zone-wise-accumulation';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-delete-dialog-extended',
    templateUrl: './supply-zone-wise-accumulation-delete-dialog-extended.component.html'
})
export class SupplyZoneWiseAccumulationDeleteDialogExtendedComponent extends SupplyZoneWiseAccumulationDeleteDialogComponent {
    constructor(
        protected supplyZoneWiseAccumulationService: SupplyZoneWiseAccumulationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyZoneWiseAccumulationService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-delete-popup-extended',
    template: ''
})
export class SupplyZoneWiseAccumulationDeletePopupExtendedComponent extends SupplyZoneWiseAccumulationDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
