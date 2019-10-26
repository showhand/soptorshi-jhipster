import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { ReceiptVoucherService } from './receipt-voucher.service';

@Component({
    selector: 'jhi-receipt-voucher-delete-dialog',
    templateUrl: './receipt-voucher-delete-dialog.component.html'
})
export class ReceiptVoucherDeleteDialogComponent {
    receiptVoucher: IReceiptVoucher;

    constructor(
        protected receiptVoucherService: ReceiptVoucherService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.receiptVoucherService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'receiptVoucherListModification',
                content: 'Deleted an receiptVoucher'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-receipt-voucher-delete-popup',
    template: ''
})
export class ReceiptVoucherDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ receiptVoucher }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReceiptVoucherDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.receiptVoucher = receiptVoucher;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/receipt-voucher', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/receipt-voucher', { outlets: { popup: null } }]);
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
