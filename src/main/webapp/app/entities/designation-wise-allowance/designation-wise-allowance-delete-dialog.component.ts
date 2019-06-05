import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';
import { DesignationWiseAllowanceService } from './designation-wise-allowance.service';

@Component({
    selector: 'jhi-designation-wise-allowance-delete-dialog',
    templateUrl: './designation-wise-allowance-delete-dialog.component.html'
})
export class DesignationWiseAllowanceDeleteDialogComponent {
    designationWiseAllowance: IDesignationWiseAllowance;

    constructor(
        protected designationWiseAllowanceService: DesignationWiseAllowanceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designationWiseAllowanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'designationWiseAllowanceListModification',
                content: 'Deleted an designationWiseAllowance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designation-wise-allowance-delete-popup',
    template: ''
})
export class DesignationWiseAllowanceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ designationWiseAllowance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DesignationWiseAllowanceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.designationWiseAllowance = designationWiseAllowance;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/designation-wise-allowance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/designation-wise-allowance', { outlets: { popup: null } }]);
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
