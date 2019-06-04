import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFineManagement } from 'app/shared/model/fine-management.model';
import { FineManagementService } from './fine-management.service';

@Component({
    selector: 'jhi-fine-management-delete-dialog',
    templateUrl: './fine-management-delete-dialog.component.html'
})
export class FineManagementDeleteDialogComponent {
    fineManagement: IFineManagement;

    constructor(
        protected fineManagementService: FineManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fineManagementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fineManagementListModification',
                content: 'Deleted an fineManagement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fine-management-delete-popup',
    template: ''
})
export class FineManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fineManagement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FineManagementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.fineManagement = fineManagement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fine-management', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fine-management', { outlets: { popup: null } }]);
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
