import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';
import { CommercialWorkOrderDetailsExtendedService } from './commercial-work-order-details-extended.service';
import {
    CommercialWorkOrderDetailsDeleteDialogComponent,
    CommercialWorkOrderDetailsDeletePopupComponent
} from 'app/entities/commercial-work-order-details';

@Component({
    selector: 'jhi-commercial-work-order-details-delete-dialog-extended',
    templateUrl: './commercial-work-order-details-delete-dialog-extended.component.html'
})
export class CommercialWorkOrderDetailsDeleteDialogExtendedComponent extends CommercialWorkOrderDetailsDeleteDialogComponent {
    commercialWorkOrderDetails: ICommercialWorkOrderDetails;

    constructor(
        protected commercialWorkOrderDetailsService: CommercialWorkOrderDetailsExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialWorkOrderDetailsService, activeModal, eventManager);
    }

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
    selector: 'jhi-commercial-work-order-details-delete-popup-extended',
    template: ''
})
export class CommercialWorkOrderDetailsDeletePopupExtendedComponent extends CommercialWorkOrderDetailsDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrderDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialWorkOrderDetailsDeleteDialogExtendedComponent as Component, {
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
