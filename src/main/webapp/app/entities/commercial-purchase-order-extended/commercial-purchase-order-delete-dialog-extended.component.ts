import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderExtendedService } from './commercial-purchase-order-extended.service';
import {
    CommercialPurchaseOrderDeleteDialogComponent,
    CommercialPurchaseOrderDeletePopupComponent
} from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-commercial-purchase-order-delete-dialog-extended',
    templateUrl: './commercial-purchase-order-delete-dialog-extended.component.html'
})
export class CommercialPurchaseOrderDeleteDialogExtendedComponent extends CommercialPurchaseOrderDeleteDialogComponent {
    commercialPurchaseOrder: ICommercialPurchaseOrder;

    constructor(
        protected commercialPurchaseOrderService: CommercialPurchaseOrderExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPurchaseOrderService, activeModal, eventManager);
    }

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
    selector: 'jhi-commercial-purchase-order-delete-popup-extended',
    template: ''
})
export class CommercialPurchaseOrderDeletePopupExtendedComponent extends CommercialPurchaseOrderDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPurchaseOrderDeleteDialogExtendedComponent as Component, {
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
