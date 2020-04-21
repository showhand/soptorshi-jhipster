import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';
import { PurchaseOrderVoucherRelationExtendedService } from './purchase-order-voucher-relation-extended.service';
import { PurchaseOrderVoucherRelationDeleteDialogComponent } from 'app/entities/purchase-order-voucher-relation';

@Component({
    selector: 'jhi-purchase-order-voucher-relation-delete-dialog',
    templateUrl: './purchase-order-voucher-relation-extended-delete-dialog.component.html'
})
export class PurchaseOrderVoucherRelationExtendedDeleteDialogComponent extends PurchaseOrderVoucherRelationDeleteDialogComponent {
    constructor(
        protected purchaseOrderVoucherRelationService: PurchaseOrderVoucherRelationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(purchaseOrderVoucherRelationService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-purchase-order-voucher-relation-delete-popup',
    template: ''
})
export class PurchaseOrderVoucherRelationExtendedDeletePopUpComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseOrderVoucherRelation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseOrderVoucherRelationExtendedDeleteDialogComponent as Component, {
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
