import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { CommercialPackagingDetailsExtendedService } from './commercial-packaging-details-extended.service';
import {
    CommercialPackagingDetailsDeleteDialogComponent,
    CommercialPackagingDetailsDeletePopupComponent
} from 'app/entities/commercial-packaging-details';

@Component({
    selector: 'jhi-commercial-packaging-details-delete-dialog-extended',
    templateUrl: './commercial-packaging-details-delete-dialog-extended.component.html'
})
export class CommercialPackagingDetailsDeleteDialogExtendedComponent extends CommercialPackagingDetailsDeleteDialogComponent {
    commercialPackagingDetails: ICommercialPackagingDetails;

    constructor(
        protected commercialPackagingDetailsService: CommercialPackagingDetailsExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPackagingDetailsService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPackagingDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPackagingDetailsListModification',
                content: 'Deleted an commercialPackagingDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-packaging-details-delete-popup-extended',
    template: ''
})
export class CommercialPackagingDetailsDeletePopupExtendedComponent extends CommercialPackagingDetailsDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackagingDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPackagingDetailsDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPackagingDetails = commercialPackagingDetails;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-packaging-details', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-packaging-details', { outlets: { popup: null } }]);
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
