import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyChallanExtendedService } from './supply-challan-extended.service';
import { SupplyChallanDeleteDialogComponent, SupplyChallanDeletePopupComponent } from 'app/entities/supply-challan';

@Component({
    selector: 'jhi-supply-challan-delete-dialog-extended',
    templateUrl: './supply-challan-delete-dialog-extended.component.html'
})
export class SupplyChallanDeleteDialogExtendedComponent extends SupplyChallanDeleteDialogComponent {
    constructor(
        protected supplyChallanService: SupplyChallanExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyChallanService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-challan-delete-popup-extended',
    template: ''
})
export class SupplyChallanDeletePopupExtendedComponent extends SupplyChallanDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
