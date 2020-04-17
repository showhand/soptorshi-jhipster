import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyShop } from 'app/shared/model/supply-shop.model';
import { SupplyShopService } from './supply-shop.service';

@Component({
    selector: 'jhi-supply-shop-delete-dialog',
    templateUrl: './supply-shop-delete-dialog.component.html'
})
export class SupplyShopDeleteDialogComponent {
    supplyShop: ISupplyShop;

    constructor(
        protected supplyShopService: SupplyShopService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyShopService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyShopListModification',
                content: 'Deleted an supplyShop'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-shop-delete-popup',
    template: ''
})
export class SupplyShopDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyShop }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyShopDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.supplyShop = supplyShop;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-shop', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-shop', { outlets: { popup: null } }]);
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
