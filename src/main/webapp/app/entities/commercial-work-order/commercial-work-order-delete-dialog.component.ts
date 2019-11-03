import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderService } from './commercial-work-order.service';

@Component({
    selector: 'jhi-commercial-work-order-delete-dialog',
    templateUrl: './commercial-work-order-delete-dialog.component.html'
})
export class CommercialWorkOrderDeleteDialogComponent {
    commercialWorkOrder: ICommercialWorkOrder;

    constructor(
        protected commercialWorkOrderService: CommercialWorkOrderService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialWorkOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialWorkOrderListModification',
                content: 'Deleted an commercialWorkOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-work-order-delete-popup',
    template: ''
})
export class CommercialWorkOrderDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialWorkOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialWorkOrder = commercialWorkOrder;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-work-order', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-work-order', { outlets: { popup: null } }]);
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
