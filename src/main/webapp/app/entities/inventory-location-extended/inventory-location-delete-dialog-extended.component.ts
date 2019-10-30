import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationExtendedService } from './inventory-location-extended.service';
import { InventoryLocationDeleteDialogComponent, InventoryLocationDeletePopupComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-delete-dialog-extended',
    templateUrl: './inventory-location-delete-dialog-extended.component.html'
})
export class InventoryLocationDeleteDialogExtendedComponent extends InventoryLocationDeleteDialogComponent {
    inventoryLocation: IInventoryLocation;

    constructor(
        protected inventoryLocationService: InventoryLocationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(inventoryLocationService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventoryLocationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'inventoryLocationListModification',
                content: 'Deleted an inventoryLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventory-location-delete-popup-extended',
    template: ''
})
export class InventoryLocationDeletePopupComponentExtended extends InventoryLocationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventoryLocation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InventoryLocationDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.inventoryLocation = inventoryLocation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/inventory-location', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/inventory-location', { outlets: { popup: null } }]);
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
