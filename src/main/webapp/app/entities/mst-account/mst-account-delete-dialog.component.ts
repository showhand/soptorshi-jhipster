import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from './mst-account.service';

@Component({
    selector: 'jhi-mst-account-delete-dialog',
    templateUrl: './mst-account-delete-dialog.component.html'
})
export class MstAccountDeleteDialogComponent {
    mstAccount: IMstAccount;

    constructor(
        protected mstAccountService: MstAccountService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mstAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mstAccountListModification',
                content: 'Deleted an mstAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mst-account-delete-popup',
    template: ''
})
export class MstAccountDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mstAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MstAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mstAccount = mstAccount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mst-account', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mst-account', { outlets: { popup: null } }]);
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
