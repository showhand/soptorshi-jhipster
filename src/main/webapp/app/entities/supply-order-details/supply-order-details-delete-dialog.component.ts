import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { SupplyOrderDetailsService } from './supply-order-details.service';

@Component({
    selector: 'jhi-supply-order-details-delete-dialog',
    templateUrl: './supply-order-details-delete-dialog.component.html'
})
export class SupplyOrderDetailsDeleteDialogComponent {
    supplyOrderDetails: ISupplyOrderDetails;

    constructor(
        protected supplyOrderDetailsService: SupplyOrderDetailsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyOrderDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyOrderDetailsListModification',
                content: 'Deleted an supplyOrderDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-order-details-delete-popup',
    template: ''
})
export class SupplyOrderDetailsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyOrderDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyOrderDetailsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyOrderDetails = supplyOrderDetails;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-order-details', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-order-details', { outlets: { popup: null } }]);
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
