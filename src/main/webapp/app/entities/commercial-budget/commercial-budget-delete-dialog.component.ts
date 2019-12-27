import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { CommercialBudgetService } from './commercial-budget.service';

@Component({
    selector: 'jhi-commercial-budget-delete-dialog',
    templateUrl: './commercial-budget-delete-dialog.component.html'
})
export class CommercialBudgetDeleteDialogComponent {
    commercialBudget: ICommercialBudget;

    constructor(
        protected commercialBudgetService: CommercialBudgetService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialBudgetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialBudgetListModification',
                content: 'Deleted an commercialBudget'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-budget-delete-popup',
    template: ''
})
export class CommercialBudgetDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialBudget }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialBudgetDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialBudget = commercialBudget;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-budget', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-budget', { outlets: { popup: null } }]);
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
