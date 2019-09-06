import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditorLedger } from 'app/shared/model/creditor-ledger.model';
import { CreditorLedgerService } from './creditor-ledger.service';

@Component({
    selector: 'jhi-creditor-ledger-delete-dialog',
    templateUrl: './creditor-ledger-delete-dialog.component.html'
})
export class CreditorLedgerDeleteDialogComponent {
    creditorLedger: ICreditorLedger;

    constructor(
        protected creditorLedgerService: CreditorLedgerService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditorLedgerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditorLedgerListModification',
                content: 'Deleted an creditorLedger'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-creditor-ledger-delete-popup',
    template: ''
})
export class CreditorLedgerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditorLedger }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditorLedgerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.creditorLedger = creditorLedger;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/creditor-ledger', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/creditor-ledger', { outlets: { popup: null } }]);
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
