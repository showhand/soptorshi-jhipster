import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherService } from './journal-voucher.service';

@Component({
    selector: 'jhi-journal-voucher-delete-dialog',
    templateUrl: './journal-voucher-delete-dialog.component.html'
})
export class JournalVoucherDeleteDialogComponent {
    journalVoucher: IJournalVoucher;

    constructor(
        protected journalVoucherService: JournalVoucherService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.journalVoucherService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'journalVoucherListModification',
                content: 'Deleted an journalVoucher'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-journal-voucher-delete-popup',
    template: ''
})
export class JournalVoucherDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalVoucher }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JournalVoucherDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.journalVoucher = journalVoucher;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/journal-voucher', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/journal-voucher', { outlets: { popup: null } }]);
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
