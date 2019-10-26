import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ContraVoucherService } from './contra-voucher.service';

@Component({
    selector: 'jhi-contra-voucher-delete-dialog',
    templateUrl: './contra-voucher-delete-dialog.component.html'
})
export class ContraVoucherDeleteDialogComponent {
    contraVoucher: IContraVoucher;

    constructor(
        protected contraVoucherService: ContraVoucherService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contraVoucherService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contraVoucherListModification',
                content: 'Deleted an contraVoucher'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contra-voucher-delete-popup',
    template: ''
})
export class ContraVoucherDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ contraVoucher }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContraVoucherDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contraVoucher = contraVoucher;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/contra-voucher', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/contra-voucher', { outlets: { popup: null } }]);
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
