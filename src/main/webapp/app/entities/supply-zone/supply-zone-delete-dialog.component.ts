import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from './supply-zone.service';

@Component({
    selector: 'jhi-supply-zone-delete-dialog',
    templateUrl: './supply-zone-delete-dialog.component.html'
})
export class SupplyZoneDeleteDialogComponent {
    supplyZone: ISupplyZone;

    constructor(
        protected supplyZoneService: SupplyZoneService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyZoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyZoneListModification',
                content: 'Deleted an supplyZone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-zone-delete-popup',
    template: ''
})
export class SupplyZoneDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyZone }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyZoneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.supplyZone = supplyZone;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-zone', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-zone', { outlets: { popup: null } }]);
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
