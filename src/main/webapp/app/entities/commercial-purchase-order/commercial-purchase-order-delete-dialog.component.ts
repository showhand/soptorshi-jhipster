import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from './commercial-purchase-order.service';

@Component({
    selector: 'jhi-commercial-purchase-order-delete-dialog',
    templateUrl: './commercial-purchase-order-delete-dialog.component.html'
})
export class CommercialPurchaseOrderDeleteDialogComponent {
    commercialPurchaseOrder: ICommercialPurchaseOrder;

    constructor(
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPurchaseOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPurchaseOrderListModification',
                content: 'Deleted an commercialPurchaseOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-purchase-order-delete-popup',
    template: ''
})
export class CommercialPurchaseOrderDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPurchaseOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPurchaseOrder = commercialPurchaseOrder;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-purchase-order', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-purchase-order', { outlets: { popup: null } }]);
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
