import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvanceManagement } from 'app/shared/model/advance-management.model';
import { AdvanceManagementService } from './advance-management.service';

@Component({
    selector: 'jhi-advance-management-delete-dialog',
    templateUrl: './advance-management-delete-dialog.component.html'
})
export class AdvanceManagementDeleteDialogComponent {
    advanceManagement: IAdvanceManagement;

    constructor(
        protected advanceManagementService: AdvanceManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.advanceManagementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'advanceManagementListModification',
                content: 'Deleted an advanceManagement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-advance-management-delete-popup',
    template: ''
})
export class AdvanceManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advanceManagement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdvanceManagementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.advanceManagement = advanceManagement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/advance-management', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/advance-management', { outlets: { popup: null } }]);
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
