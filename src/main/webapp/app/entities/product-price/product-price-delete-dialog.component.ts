import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPrice } from 'app/shared/model/product-price.model';
import { ProductPriceService } from './product-price.service';

@Component({
    selector: 'jhi-product-price-delete-dialog',
    templateUrl: './product-price-delete-dialog.component.html'
})
export class ProductPriceDeleteDialogComponent {
    productPrice: IProductPrice;

    constructor(
        protected productPriceService: ProductPriceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productPriceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productPriceListModification',
                content: 'Deleted an productPrice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-price-delete-popup',
    template: ''
})
export class ProductPriceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productPrice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductPriceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productPrice = productPrice;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-price', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-price', { outlets: { popup: null } }]);
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
