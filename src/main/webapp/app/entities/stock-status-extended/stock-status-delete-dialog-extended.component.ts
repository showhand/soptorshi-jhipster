import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { StockStatusExtendedService } from './stock-status-extended.service';
import { StockStatusDeleteDialogComponent, StockStatusDeletePopupComponent } from 'app/entities/stock-status';

@Component({
    selector: 'jhi-stock-status-delete-dialog-extended',
    templateUrl: './stock-status-delete-dialog-extended.component.html'
})
export class StockStatusDeleteDialogExtendedComponent extends StockStatusDeleteDialogComponent {
    constructor(
        protected stockStatusService: StockStatusExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(stockStatusService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-stock-status-delete-popup-extended',
    template: ''
})
export class StockStatusDeletePopupExtendedComponent extends StockStatusDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
