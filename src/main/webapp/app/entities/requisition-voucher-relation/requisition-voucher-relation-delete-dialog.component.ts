import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationService } from './requisition-voucher-relation.service';

@Component({
    selector: 'jhi-requisition-voucher-relation-delete-dialog',
    templateUrl: './requisition-voucher-relation-delete-dialog.component.html'
})
export class RequisitionVoucherRelationDeleteDialogComponent {
    requisitionVoucherRelation: IRequisitionVoucherRelation;

    constructor(
        protected requisitionVoucherRelationService: RequisitionVoucherRelationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requisitionVoucherRelationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'requisitionVoucherRelationListModification',
                content: 'Deleted an requisitionVoucherRelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-requisition-voucher-relation-delete-popup',
    template: ''
})
export class RequisitionVoucherRelationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisitionVoucherRelation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RequisitionVoucherRelationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.requisitionVoucherRelation = requisitionVoucherRelation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/requisition-voucher-relation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/requisition-voucher-relation', { outlets: { popup: null } }]);
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
