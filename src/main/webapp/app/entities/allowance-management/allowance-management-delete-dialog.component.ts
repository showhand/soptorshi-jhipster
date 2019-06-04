import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllowanceManagement } from 'app/shared/model/allowance-management.model';
import { AllowanceManagementService } from './allowance-management.service';

@Component({
    selector: 'jhi-allowance-management-delete-dialog',
    templateUrl: './allowance-management-delete-dialog.component.html'
})
export class AllowanceManagementDeleteDialogComponent {
    allowanceManagement: IAllowanceManagement;

    constructor(
        protected allowanceManagementService: AllowanceManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.allowanceManagementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'allowanceManagementListModification',
                content: 'Deleted an allowanceManagement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-allowance-management-delete-popup',
    template: ''
})
export class AllowanceManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ allowanceManagement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AllowanceManagementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.allowanceManagement = allowanceManagement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/allowance-management', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/allowance-management', { outlets: { popup: null } }]);
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
