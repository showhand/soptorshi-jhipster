import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionService } from './dt-transaction.service';

@Component({
    selector: 'jhi-dt-transaction-delete-dialog',
    templateUrl: './dt-transaction-delete-dialog.component.html'
})
export class DtTransactionDeleteDialogComponent {
    dtTransaction: IDtTransaction;

    constructor(
        protected dtTransactionService: DtTransactionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dtTransactionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dtTransactionListModification',
                content: 'Deleted an dtTransaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dt-transaction-delete-popup',
    template: ''
})
export class DtTransactionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dtTransaction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DtTransactionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.dtTransaction = dtTransaction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/dt-transaction', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/dt-transaction', { outlets: { popup: null } }]);
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
