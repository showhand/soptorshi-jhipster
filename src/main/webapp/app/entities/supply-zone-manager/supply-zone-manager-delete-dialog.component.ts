import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from './supply-zone-manager.service';

@Component({
    selector: 'jhi-supply-zone-manager-delete-dialog',
    templateUrl: './supply-zone-manager-delete-dialog.component.html'
})
export class SupplyZoneManagerDeleteDialogComponent {
    supplyZoneManager: ISupplyZoneManager;

    constructor(
        protected supplyZoneManagerService: SupplyZoneManagerService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyZoneManagerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyZoneManagerListModification',
                content: 'Deleted an supplyZoneManager'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-zone-manager-delete-popup',
    template: ''
})
export class SupplyZoneManagerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyZoneManager }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyZoneManagerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyZoneManager = supplyZoneManager;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-zone-manager', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-zone-manager', { outlets: { popup: null } }]);
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
