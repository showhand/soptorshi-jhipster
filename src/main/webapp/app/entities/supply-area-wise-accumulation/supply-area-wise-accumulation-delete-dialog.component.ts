import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';
import { SupplyAreaWiseAccumulationService } from './supply-area-wise-accumulation.service';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-delete-dialog',
    templateUrl: './supply-area-wise-accumulation-delete-dialog.component.html'
})
export class SupplyAreaWiseAccumulationDeleteDialogComponent {
    supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation;

    constructor(
        protected supplyAreaWiseAccumulationService: SupplyAreaWiseAccumulationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyAreaWiseAccumulationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyAreaWiseAccumulationListModification',
                content: 'Deleted an supplyAreaWiseAccumulation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-area-wise-accumulation-delete-popup',
    template: ''
})
export class SupplyAreaWiseAccumulationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyAreaWiseAccumulation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyAreaWiseAccumulationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyAreaWiseAccumulation = supplyAreaWiseAccumulation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-area-wise-accumulation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-area-wise-accumulation', { outlets: { popup: null } }]);
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
