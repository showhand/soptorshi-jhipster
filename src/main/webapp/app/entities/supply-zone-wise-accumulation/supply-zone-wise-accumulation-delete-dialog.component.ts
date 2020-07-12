import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';
import { SupplyZoneWiseAccumulationService } from './supply-zone-wise-accumulation.service';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-delete-dialog',
    templateUrl: './supply-zone-wise-accumulation-delete-dialog.component.html'
})
export class SupplyZoneWiseAccumulationDeleteDialogComponent {
    supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation;

    constructor(
        protected supplyZoneWiseAccumulationService: SupplyZoneWiseAccumulationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyZoneWiseAccumulationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyZoneWiseAccumulationListModification',
                content: 'Deleted an supplyZoneWiseAccumulation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-delete-popup',
    template: ''
})
export class SupplyZoneWiseAccumulationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyZoneWiseAccumulation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyZoneWiseAccumulationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyZoneWiseAccumulation = supplyZoneWiseAccumulation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-zone-wise-accumulation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-zone-wise-accumulation', { outlets: { popup: null } }]);
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
