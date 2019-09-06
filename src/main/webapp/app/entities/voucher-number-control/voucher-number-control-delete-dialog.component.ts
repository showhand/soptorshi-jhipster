import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { VoucherNumberControlService } from './voucher-number-control.service';

@Component({
    selector: 'jhi-voucher-number-control-delete-dialog',
    templateUrl: './voucher-number-control-delete-dialog.component.html'
})
export class VoucherNumberControlDeleteDialogComponent {
    voucherNumberControl: IVoucherNumberControl;

    constructor(
        protected voucherNumberControlService: VoucherNumberControlService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voucherNumberControlService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'voucherNumberControlListModification',
                content: 'Deleted an voucherNumberControl'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-voucher-number-control-delete-popup',
    template: ''
})
export class VoucherNumberControlDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ voucherNumberControl }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VoucherNumberControlDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.voucherNumberControl = voucherNumberControl;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/voucher-number-control', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/voucher-number-control', { outlets: { popup: null } }]);
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
