import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesService } from './purchase-order-messages.service';

@Component({
    selector: 'jhi-purchase-order-messages-delete-dialog',
    templateUrl: './purchase-order-messages-delete-dialog.component.html'
})
export class PurchaseOrderMessagesDeleteDialogComponent {
    purchaseOrderMessages: IPurchaseOrderMessages;

    constructor(
        protected purchaseOrderMessagesService: PurchaseOrderMessagesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseOrderMessagesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseOrderMessagesListModification',
                content: 'Deleted an purchaseOrderMessages'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-order-messages-delete-popup',
    template: ''
})
export class PurchaseOrderMessagesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseOrderMessages }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseOrderMessagesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchaseOrderMessages = purchaseOrderMessages;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/purchase-order-messages', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/purchase-order-messages', { outlets: { popup: null } }]);
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
