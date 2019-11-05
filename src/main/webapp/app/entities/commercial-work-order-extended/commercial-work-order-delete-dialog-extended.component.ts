import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderExtendedService } from './commercial-work-order-extended.service';
import { CommercialWorkOrderDeleteDialogComponent, CommercialWorkOrderDeletePopupComponent } from 'app/entities/commercial-work-order';

@Component({
    selector: 'jhi-commercial-work-order-delete-dialog-extended',
    templateUrl: './commercial-work-order-delete-dialog-extended.component.html'
})
export class CommercialWorkOrderDeleteDialogExtendedComponent extends CommercialWorkOrderDeleteDialogComponent {
    commercialWorkOrder: ICommercialWorkOrder;

    constructor(
        protected commercialWorkOrderService: CommercialWorkOrderExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialWorkOrderService, activeModal, eventManager);
    }

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
    selector: 'jhi-commercial-work-order-delete-popup-extended',
    template: ''
})
export class CommercialWorkOrderDeletePopupExtendedComponent extends CommercialWorkOrderDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialWorkOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialWorkOrderDeleteDialogExtendedComponent as Component, {
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
