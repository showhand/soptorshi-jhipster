import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkOrder } from 'app/shared/model/work-order.model';
import { WorkOrderService } from './work-order.service';

@Component({
    selector: 'jhi-work-order-delete-dialog',
    templateUrl: './work-order-delete-dialog.component.html'
})
export class WorkOrderDeleteDialogComponent {
    workOrder: IWorkOrder;

    constructor(
        protected workOrderService: WorkOrderService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'workOrderListModification',
                content: 'Deleted an workOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-work-order-delete-popup',
    template: ''
})
export class WorkOrderDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ workOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WorkOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.workOrder = workOrder;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/work-order', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/work-order', { outlets: { popup: null } }]);
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
