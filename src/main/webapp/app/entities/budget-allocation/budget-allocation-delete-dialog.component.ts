import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBudgetAllocation } from 'app/shared/model/budget-allocation.model';
import { BudgetAllocationService } from './budget-allocation.service';

@Component({
    selector: 'jhi-budget-allocation-delete-dialog',
    templateUrl: './budget-allocation-delete-dialog.component.html'
})
export class BudgetAllocationDeleteDialogComponent {
    budgetAllocation: IBudgetAllocation;

    constructor(
        protected budgetAllocationService: BudgetAllocationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.budgetAllocationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'budgetAllocationListModification',
                content: 'Deleted an budgetAllocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-budget-allocation-delete-popup',
    template: ''
})
export class BudgetAllocationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budgetAllocation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BudgetAllocationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.budgetAllocation = budgetAllocation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/budget-allocation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/budget-allocation', { outlets: { popup: null } }]);
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
