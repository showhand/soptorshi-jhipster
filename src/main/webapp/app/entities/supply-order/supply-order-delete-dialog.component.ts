import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from './supply-order.service';

@Component({
    selector: 'jhi-supply-order-delete-dialog',
    templateUrl: './supply-order-delete-dialog.component.html'
})
export class SupplyOrderDeleteDialogComponent {
    supplyOrder: ISupplyOrder;

    constructor(
        protected supplyOrderService: SupplyOrderService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyOrderListModification',
                content: 'Deleted an supplyOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-order-delete-popup',
    template: ''
})
export class SupplyOrderDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyOrder = supplyOrder;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-order', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-order', { outlets: { popup: null } }]);
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
