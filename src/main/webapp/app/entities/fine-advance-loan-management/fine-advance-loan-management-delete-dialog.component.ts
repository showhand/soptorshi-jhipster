import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { FineAdvanceLoanManagementService } from './fine-advance-loan-management.service';
import { FineService } from 'app/entities/fine';
import { AdvanceService } from 'app/entities/advance';
import { LoanService } from 'app/entities/loan';
import { ProvidentFundService } from 'app/entities/provident-fund';

@Component({
    selector: 'jhi-fine-advance-loan-management-delete-dialog',
    templateUrl: './fine-advance-loan-management-delete-dialog.component.html'
})
export class FineAdvanceLoanManagementDeleteDialogComponent {
    fineAdvanceLoanManagement: IFineAdvanceLoanManagement;

    constructor(
        protected fineAdvanceLoanManagementService: FineAdvanceLoanManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fineAdvanceLoanManagementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fineAdvanceLoanManagementListModification',
                content: 'Deleted an fineAdvanceLoanManagement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fine-advance-loan-management-delete-popup',
    template: ''
})
export class FineAdvanceLoanManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fineAdvanceLoanManagement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FineAdvanceLoanManagementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.fineAdvanceLoanManagement = fineAdvanceLoanManagement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fine-advance-loan-management', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fine-advance-loan-management', { outlets: { popup: null } }]);
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
