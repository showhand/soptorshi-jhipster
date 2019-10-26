import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherService } from './payment-voucher.service';

@Component({
    selector: 'jhi-payment-voucher-delete-dialog',
    templateUrl: './payment-voucher-delete-dialog.component.html'
})
export class PaymentVoucherDeleteDialogComponent {
    paymentVoucher: IPaymentVoucher;

    constructor(
        protected paymentVoucherService: PaymentVoucherService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paymentVoucherService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'paymentVoucherListModification',
                content: 'Deleted an paymentVoucher'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payment-voucher-delete-popup',
    template: ''
})
export class PaymentVoucherDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paymentVoucher }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PaymentVoucherDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.paymentVoucher = paymentVoucher;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/payment-voucher', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/payment-voucher', { outlets: { popup: null } }]);
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
