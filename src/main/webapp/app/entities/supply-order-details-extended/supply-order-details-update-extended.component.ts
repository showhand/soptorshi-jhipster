import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyOrderDetailsExtendedService } from './supply-order-details-extended.service';
import { SupplyOrderService } from 'app/entities/supply-order';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { SupplyOrderDetailsUpdateComponent } from 'app/entities/supply-order-details';

@Component({
    selector: 'jhi-supply-order-details-update-extended',
    templateUrl: './supply-order-details-update-extended.component.html'
})
export class SupplyOrderDetailsUpdateExtendedComponent extends SupplyOrderDetailsUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyOrderDetailsService: SupplyOrderDetailsExtendedService,
        protected supplyOrderService: SupplyOrderService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {
        super(jhiAlertService, supplyOrderDetailsService, supplyOrderService, productCategoryService, productService, activatedRoute);
    }

    previousState() {
        if (this.supplyOrderDetails && this.supplyOrderDetails.supplyOrderId) {
            this.router.navigate(['/supply-order', this.supplyOrderDetails.supplyOrderId, 'edit']);
        } else {
            window.history.back();
        }
    }
}
