import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { CommercialPackagingDetailsService } from './commercial-packaging-details.service';

@Component({
    selector: 'jhi-commercial-packaging-details-delete-dialog',
    templateUrl: './commercial-packaging-details-delete-dialog.component.html'
})
export class CommercialPackagingDetailsDeleteDialogComponent {
    commercialPackagingDetails: ICommercialPackagingDetails;

    constructor(
        protected commercialPackagingDetailsService: CommercialPackagingDetailsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-commercial-packaging-details-delete-popup',
    template: ''
})
export class CommercialPackagingDetailsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackagingDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPackagingDetailsDeleteDialogComponent as Component, {
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
