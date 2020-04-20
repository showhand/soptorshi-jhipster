import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplyShopExtendedService } from './supply-shop-extended.service';
import { SupplyShopDeleteDialogComponent, SupplyShopDeletePopupComponent } from 'app/entities/supply-shop';

@Component({
    selector: 'jhi-supply-shop-delete-dialog-extended',
    templateUrl: './supply-shop-delete-dialog-extended.component.html'
})
export class SupplyShopDeleteDialogExtendedComponent extends SupplyShopDeleteDialogComponent {
    constructor(
        protected supplyShopService: SupplyShopExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplyShopService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-shop-delete-popup-extended',
    template: ''
})
export class SupplyShopDeletePopupExtendedComponent extends SupplyShopDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
