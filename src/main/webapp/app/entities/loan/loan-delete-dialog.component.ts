import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoan } from 'app/shared/model/loan.model';
import { LoanService } from './loan.service';

@Component({
    selector: 'jhi-loan-delete-dialog',
    templateUrl: './loan-delete-dialog.component.html'
})
export class LoanDeleteDialogComponent {
    loan: ILoan;

    constructor(protected loanService: LoanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.loanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'loanListModification',
                content: 'Deleted an loan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-loan-delete-popup',
    template: ''
})
export class LoanDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ loan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LoanDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.loan = loan;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/loan', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/loan', { outlets: { popup: null } }]);
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
