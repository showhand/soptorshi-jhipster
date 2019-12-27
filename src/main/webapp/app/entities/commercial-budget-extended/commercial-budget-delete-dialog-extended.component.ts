import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommercialBudgetExtendedService } from './commercial-budget-extended.service';
import { CommercialBudgetDeleteDialogComponent, CommercialBudgetDeletePopupComponent } from 'app/entities/commercial-budget';

@Component({
    selector: 'jhi-commercial-budget-delete-dialog-extended',
    templateUrl: './commercial-budget-delete-dialog-extended.component.html'
})
export class CommercialBudgetDeleteDialogExtendedComponent extends CommercialBudgetDeleteDialogComponent {
    constructor(
        protected commercialBudgetService: CommercialBudgetExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialBudgetService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-commercial-budget-delete-popup-extended',
    template: ''
})
export class CommercialBudgetDeletePopupExtendedComponent extends CommercialBudgetDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
