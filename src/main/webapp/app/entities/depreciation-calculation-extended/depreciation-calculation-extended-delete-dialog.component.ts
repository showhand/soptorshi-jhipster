import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationExtendedService } from './depreciation-calculation-extended.service';
import {
    DepreciationCalculationDeleteDialogComponent,
    DepreciationCalculationDeletePopupComponent
} from 'app/entities/depreciation-calculation';

@Component({
    selector: 'jhi-depreciation-calculation-delete-dialog',
    templateUrl: './depreciation-calculation-extended-delete-dialog.component.html'
})
export class DepreciationCalculationExtendedDeleteDialogComponent extends DepreciationCalculationDeleteDialogComponent {
    constructor(
        protected depreciationCalculationService: DepreciationCalculationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(depreciationCalculationService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-depreciation-calculation-delete-popup',
    template: ''
})
export class DepreciationCalculationExtendedDeletePopupComponent extends DepreciationCalculationDeletePopupComponent
    implements OnInit, OnDestroy {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
