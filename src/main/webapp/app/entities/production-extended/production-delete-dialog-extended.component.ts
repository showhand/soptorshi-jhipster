import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ProductionExtendedService } from './production-extended.service';
import { ProductionDeleteDialogComponent, ProductionDeletePopupComponent } from 'app/entities/production';

@Component({
    selector: 'jhi-production-delete-dialog-extended',
    templateUrl: './production-delete-dialog-extended.component.html'
})
export class ProductionDeleteDialogExtendedComponent extends ProductionDeleteDialogComponent {
    constructor(
        protected productionService: ProductionExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(productionService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-production-delete-popup-extended',
    template: ''
})
export class ProductionDeletePopupExtendedComponent extends ProductionDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
