import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from './designation.service';

@Component({
    selector: 'jhi-designation-delete-dialog',
    templateUrl: './designation-delete-dialog.component.html'
})
export class DesignationDeleteDialogComponent {
    designation: IDesignation;

    constructor(
        protected designationService: DesignationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'designationListModification',
                content: 'Deleted an designation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designation-delete-popup',
    template: ''
})
export class DesignationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ designation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DesignationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.designation = designation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/designation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/designation', { outlets: { popup: null } }]);
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
