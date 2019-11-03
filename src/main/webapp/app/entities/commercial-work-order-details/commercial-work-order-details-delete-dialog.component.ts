import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { CommercialWorkOrderDetailsService } from './commercial-work-order-details.service';

@Component({
    selector: 'jhi-commercial-work-order-details-delete-dialog',
    templateUrl: './commercial-work-order-details-delete-dialog.component.html'
})
export class CommercialWorkOrderDetailsDeleteDialogComponent {
    commercialWorkOrderDetails: ICommercialWorkOrderDetails;

    constructor(
        protected commercialWorkOrderDetailsService: CommercialWorkOrderDetailsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialWorkOrderDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialWorkOrderDetailsListModification',
                content: 'Deleted an commercialWorkOrderDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-work-order-details-delete-popup',
    template: ''
})
export class CommercialWorkOrderDetailsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrderDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialWorkOrderDetailsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialWorkOrderDetails = commercialWorkOrderDetails;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-work-order-details', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-work-order-details', { outlets: { popup: null } }]);
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
