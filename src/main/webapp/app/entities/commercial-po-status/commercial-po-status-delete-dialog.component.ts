import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusService } from './commercial-po-status.service';

@Component({
    selector: 'jhi-commercial-po-status-delete-dialog',
    templateUrl: './commercial-po-status-delete-dialog.component.html'
})
export class CommercialPoStatusDeleteDialogComponent {
    commercialPoStatus: ICommercialPoStatus;

    constructor(
        protected commercialPoStatusService: CommercialPoStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-commercial-po-status-delete-popup',
    template: ''
})
export class CommercialPoStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPoStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPoStatusDeleteDialogComponent as Component, {
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
