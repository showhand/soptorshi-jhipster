import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusExtendedService } from './commercial-po-status-extended.service';
import { CommercialPoStatusDeleteDialogComponent, CommercialPoStatusDeletePopupComponent } from 'app/entities/commercial-po-status';

@Component({
    selector: 'jhi-commercial-po-status-delete-dialog-extended',
    templateUrl: './commercial-po-status-delete-dialog-extended.component.html'
})
export class CommercialPoStatusDeleteDialogExtendedComponent extends CommercialPoStatusDeleteDialogComponent {
    commercialPoStatus: ICommercialPoStatus;

    constructor(
        protected commercialPoStatusService: CommercialPoStatusExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPoStatusService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPoStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPoStatusListModification',
                content: 'Deleted an commercialPoStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-po-status-delete-popup-extended',
    template: ''
})
export class CommercialPoStatusDeletePopupExtendedComponent extends CommercialPoStatusDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPoStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPoStatusDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPoStatus = commercialPoStatus;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-po-status', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-po-status', { outlets: { popup: null } }]);
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
