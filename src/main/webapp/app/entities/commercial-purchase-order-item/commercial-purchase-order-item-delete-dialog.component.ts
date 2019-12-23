import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';
import { CommercialPurchaseOrderItemService } from './commercial-purchase-order-item.service';

@Component({
    selector: 'jhi-commercial-purchase-order-item-delete-dialog',
    templateUrl: './commercial-purchase-order-item-delete-dialog.component.html'
})
export class CommercialPurchaseOrderItemDeleteDialogComponent {
    commercialPurchaseOrderItem: ICommercialPurchaseOrderItem;

    constructor(
        protected commercialPurchaseOrderItemService: CommercialPurchaseOrderItemService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-commercial-purchase-order-item-delete-popup',
    template: ''
})
export class CommercialPurchaseOrderItemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPurchaseOrderItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPurchaseOrderItemDeleteDialogComponent as Component, {
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
