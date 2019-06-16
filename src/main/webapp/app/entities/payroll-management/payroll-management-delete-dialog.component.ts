import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPayrollManagement } from 'app/shared/model/payroll-management.model';
import { PayrollManagementService } from './payroll-management.service';

@Component({
    selector: 'jhi-payroll-management-delete-dialog',
    templateUrl: './payroll-management-delete-dialog.component.html'
})
export class PayrollManagementDeleteDialogComponent {
    payrollManagement: IPayrollManagement;

    constructor(
        protected payrollManagementService: PayrollManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        /*this.payrollManagementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'payrollManagementListModification',
                content: 'Deleted an payrollManagement'
            });
            this.activeModal.dismiss(true);
        });*/
    }
}

@Component({
    selector: 'jhi-payroll-management-delete-popup',
    template: ''
})
export class PayrollManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ payrollManagement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PayrollManagementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.payrollManagement = payrollManagement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/payroll-management', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/payroll-management', { outlets: { popup: null } }]);
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
