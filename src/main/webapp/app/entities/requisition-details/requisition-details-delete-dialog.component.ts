import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionDetailsService } from './requisition-details.service';

@Component({
    selector: 'jhi-requisition-details-delete-dialog',
    templateUrl: './requisition-details-delete-dialog.component.html'
})
export class RequisitionDetailsDeleteDialogComponent {
    requisitionDetails: IRequisitionDetails;

    constructor(
        protected requisitionDetailsService: RequisitionDetailsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requisitionDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'requisitionDetailsListModification',
                content: 'Deleted an requisitionDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-requisition-details-delete-popup',
    template: ''
})
export class RequisitionDetailsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisitionDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RequisitionDetailsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.requisitionDetails = requisitionDetails;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/requisition-details', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/requisition-details', { outlets: { popup: null } }]);
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
