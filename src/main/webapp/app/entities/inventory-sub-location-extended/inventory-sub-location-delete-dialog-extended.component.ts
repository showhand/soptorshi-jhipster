import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationExtendedService } from './inventory-sub-location-extended.service';
import { InventorySubLocationDeleteDialogComponent, InventorySubLocationDeletePopupComponent } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-inventory-sub-location-delete-dialog-extended',
    templateUrl: './inventory-sub-location-delete-dialog-extended.component.html'
})
export class InventorySubLocationDeleteDialogExtendedComponent extends InventorySubLocationDeleteDialogComponent {
    inventorySubLocation: IInventorySubLocation;

    constructor(
        protected inventorySubLocationService: InventorySubLocationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(inventorySubLocationService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventorySubLocationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'inventorySubLocationListModification',
                content: 'Deleted an inventorySubLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventory-sub-location-delete-popup-extended',
    template: ''
})
export class InventorySubLocationDeletePopupComponentExtended extends InventorySubLocationDeletePopupComponent
    implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventorySubLocation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InventorySubLocationDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.inventorySubLocation = inventorySubLocation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/inventory-sub-location', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/inventory-sub-location', { outlets: { popup: null } }]);
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
