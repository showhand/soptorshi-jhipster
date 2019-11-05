import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';
import { CommercialPurchaseOrderItemExtendedService } from './commercial-purchase-order-item-extended.service';
import {
    CommercialPurchaseOrderItemDeleteDialogComponent,
    CommercialPurchaseOrderItemDeletePopupComponent
} from 'app/entities/commercial-purchase-order-item';

@Component({
    selector: 'jhi-commercial-purchase-order-item-delete-dialog-extended',
    templateUrl: './commercial-purchase-order-item-delete-dialog-extended.component.html'
})
export class CommercialPurchaseOrderItemDeleteDialogExtendedComponent extends CommercialPurchaseOrderItemDeleteDialogComponent {
    commercialPurchaseOrderItem: ICommercialPurchaseOrderItem;

    constructor(
        protected commercialPurchaseOrderItemService: CommercialPurchaseOrderItemExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPurchaseOrderItemService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPurchaseOrderItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPurchaseOrderItemListModification',
                content: 'Deleted an commercialPurchaseOrderItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-purchase-order-item-delete-popup-extended',
    template: ''
})
export class CommercialPurchaseOrderItemDeletePopupExtendedComponent extends CommercialPurchaseOrderItemDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrderItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPurchaseOrderItemDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPurchaseOrderItem = commercialPurchaseOrderItem;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-purchase-order-item', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-purchase-order-item', { outlets: { popup: null } }]);
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
