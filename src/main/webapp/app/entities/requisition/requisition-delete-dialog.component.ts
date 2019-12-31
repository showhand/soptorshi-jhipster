import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from './requisition.service';

@Component({
    selector: 'jhi-requisition-delete-dialog',
    templateUrl: './requisition-delete-dialog.component.html'
})
export class RequisitionDeleteDialogComponent {
    requisition: IRequisition;

    constructor(
        protected requisitionService: RequisitionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requisitionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'requisitionListModification',
                content: 'Deleted an requisition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-requisition-delete-popup',
    template: ''
})
export class RequisitionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisition }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RequisitionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.requisition = requisition;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/requisition', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/requisition', { outlets: { popup: null } }]);
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
