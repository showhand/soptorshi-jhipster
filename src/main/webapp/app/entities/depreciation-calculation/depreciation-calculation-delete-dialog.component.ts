import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationService } from './depreciation-calculation.service';

@Component({
    selector: 'jhi-depreciation-calculation-delete-dialog',
    templateUrl: './depreciation-calculation-delete-dialog.component.html'
})
export class DepreciationCalculationDeleteDialogComponent {
    depreciationCalculation: IDepreciationCalculation;

    constructor(
        protected depreciationCalculationService: DepreciationCalculationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depreciationCalculationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'depreciationCalculationListModification',
                content: 'Deleted an depreciationCalculation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-depreciation-calculation-delete-popup',
    template: ''
})
export class DepreciationCalculationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depreciationCalculation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepreciationCalculationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.depreciationCalculation = depreciationCalculation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/depreciation-calculation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/depreciation-calculation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
