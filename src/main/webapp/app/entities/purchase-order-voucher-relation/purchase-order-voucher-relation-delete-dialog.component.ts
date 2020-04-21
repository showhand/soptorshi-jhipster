import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationService } from './purchase-order-voucher-relation.service';

@Component({
    selector: 'jhi-purchase-order-voucher-relation-delete-dialog',
    templateUrl: './purchase-order-voucher-relation-delete-dialog.component.html'
})
export class PurchaseOrderVoucherRelationDeleteDialogComponent {
    purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation;

    constructor(
        protected purchaseOrderVoucherRelationService: PurchaseOrderVoucherRelationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseOrderVoucherRelationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseOrderVoucherRelationListModification',
                content: 'Deleted an purchaseOrderVoucherRelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-order-voucher-relation-delete-popup',
    template: ''
})
export class PurchaseOrderVoucherRelationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseOrderVoucherRelation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseOrderVoucherRelationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchaseOrderVoucherRelation = purchaseOrderVoucherRelation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/purchase-order-voucher-relation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/purchase-order-voucher-relation', { outlets: { popup: null } }]);
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
