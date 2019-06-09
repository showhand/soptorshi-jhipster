import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoanManagement } from 'app/shared/model/loan-management.model';
import { LoanManagementService } from './loan-management.service';

@Component({
    selector: 'jhi-loan-management-delete-dialog',
    templateUrl: './loan-management-delete-dialog.component.html'
})
export class LoanManagementDeleteDialogComponent {
    loanManagement: ILoanManagement;

    constructor(
        protected loanManagementService: LoanManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.loanManagementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'loanManagementListModification',
                content: 'Deleted an loanManagement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-loan-management-delete-popup',
    template: ''
})
export class LoanManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ loanManagement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LoanManagementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.loanManagement = loanManagement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/loan-management', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/loan-management', { outlets: { popup: null } }]);
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
