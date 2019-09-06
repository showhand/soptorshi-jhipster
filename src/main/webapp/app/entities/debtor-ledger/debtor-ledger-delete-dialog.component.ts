import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDebtorLedger } from 'app/shared/model/debtor-ledger.model';
import { DebtorLedgerService } from './debtor-ledger.service';

@Component({
    selector: 'jhi-debtor-ledger-delete-dialog',
    templateUrl: './debtor-ledger-delete-dialog.component.html'
})
export class DebtorLedgerDeleteDialogComponent {
    debtorLedger: IDebtorLedger;

    constructor(
        protected debtorLedgerService: DebtorLedgerService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.debtorLedgerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'debtorLedgerListModification',
                content: 'Deleted an debtorLedger'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-debtor-ledger-delete-popup',
    template: ''
})
export class DebtorLedgerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ debtorLedger }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DebtorLedgerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.debtorLedger = debtorLedger;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/debtor-ledger', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/debtor-ledger', { outlets: { popup: null } }]);
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
