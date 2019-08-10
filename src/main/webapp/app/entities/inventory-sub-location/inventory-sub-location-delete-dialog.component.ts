import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from './inventory-sub-location.service';

@Component({
    selector: 'jhi-inventory-sub-location-delete-dialog',
    templateUrl: './inventory-sub-location-delete-dialog.component.html'
})
export class InventorySubLocationDeleteDialogComponent {
    inventorySubLocation: IInventorySubLocation;

    constructor(
        protected inventorySubLocationService: InventorySubLocationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-inventory-sub-location-delete-popup',
    template: ''
})
export class InventorySubLocationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventorySubLocation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InventorySubLocationDeleteDialogComponent as Component, {
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
