import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationServiceExtended } from './inventory-location.service.extended';
import { InventoryLocationDeleteDialogComponent, InventoryLocationDeletePopupComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-delete-dialog-extended',
    templateUrl: './inventory-location-delete-dialog.component.extended.html'
})
export class InventoryLocationDeleteDialogComponentExtended extends InventoryLocationDeleteDialogComponent {
    inventoryLocation: IInventoryLocation;

    constructor(
        protected inventoryLocationService: InventoryLocationServiceExtended,
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
                this.ngbModalRef = this.modalService.open(InventoryLocationDeleteDialogComponentExtended as Component, {
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
