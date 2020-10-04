import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { InventorySubLocationExtendedService } from './inventory-sub-location-extended.service';
import { InventorySubLocationDeleteDialogComponent, InventorySubLocationDeletePopupComponent } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-inventory-sub-location-delete-dialog-extended',
    templateUrl: './inventory-sub-location-delete-dialog-extended.component.html'
})
export class InventorySubLocationDeleteDialogExtendedComponent extends InventorySubLocationDeleteDialogComponent {
    constructor(
        protected inventorySubLocationService: InventorySubLocationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(inventorySubLocationService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-inventory-sub-location-delete-popup-extended',
    template: ''
})
export class InventorySubLocationDeletePopupExtendedComponent extends InventorySubLocationDeletePopupComponent implements OnInit {
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
}
