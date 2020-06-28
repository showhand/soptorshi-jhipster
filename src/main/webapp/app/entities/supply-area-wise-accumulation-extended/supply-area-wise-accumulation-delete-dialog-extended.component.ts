import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyAreaWiseAccumulationExtendedService } from './supply-area-wise-accumulation-extended.service';
import {
    SupplyAreaWiseAccumulationDeleteDialogComponent,
    SupplyAreaWiseAccumulationDeletePopupComponent
} from 'app/entities/supply-area-wise-accumulation';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-delete-dialog-extended',
    templateUrl: './supply-area-wise-accumulation-delete-dialog-extended.component.html'
})
export class SupplyAreaWiseAccumulationDeleteDialogExtendedComponent extends SupplyAreaWiseAccumulationDeleteDialogComponent {
    constructor(
        protected supplyAreaWiseAccumulationService: SupplyAreaWiseAccumulationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyAreaWiseAccumulationService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-area-wise-accumulation-delete-popup-extended',
    template: ''
})
export class SupplyAreaWiseAccumulationDeletePopupExtendedComponent extends SupplyAreaWiseAccumulationDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
