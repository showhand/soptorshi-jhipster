import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesService } from './requisition-messages.service';

@Component({
    selector: 'jhi-requisition-messages-delete-dialog',
    templateUrl: './requisition-messages-delete-dialog.component.html'
})
export class RequisitionMessagesDeleteDialogComponent {
    requisitionMessages: IRequisitionMessages;

    constructor(
        protected requisitionMessagesService: RequisitionMessagesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requisitionMessagesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'requisitionMessagesListModification',
                content: 'Deleted an requisitionMessages'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-requisition-messages-delete-popup',
    template: ''
})
export class RequisitionMessagesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisitionMessages }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RequisitionMessagesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.requisitionMessages = requisitionMessages;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/requisition-messages', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/requisition-messages', { outlets: { popup: null } }]);
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
