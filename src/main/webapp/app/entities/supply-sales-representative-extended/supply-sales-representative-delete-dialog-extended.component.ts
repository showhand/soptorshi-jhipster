import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { SupplySalesRepresentativeExtendedService } from './supply-sales-representative-extended.service';
import {
    SupplySalesRepresentativeDeleteDialogComponent,
    SupplySalesRepresentativeDeletePopupComponent
} from 'app/entities/supply-sales-representative';

@Component({
    selector: 'jhi-supply-sales-representative-delete-dialog-extended',
    templateUrl: './supply-sales-representative-delete-dialog-extended.component.html'
})
export class SupplySalesRepresentativeDeleteDialogExtendedComponent extends SupplySalesRepresentativeDeleteDialogComponent {
    constructor(
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(supplySalesRepresentativeService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-supply-sales-representative-delete-popup-extended',
    template: ''
})
export class SupplySalesRepresentativeDeletePopupExtendedComponent extends SupplySalesRepresentativeDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
