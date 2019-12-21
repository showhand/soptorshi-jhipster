import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { StockInProcessExtendedService } from './stock-in-process-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockInProcessUpdateComponent } from 'app/entities/stock-in-process';
import { VendorService } from 'app/entities/vendor';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-stock-in-process-update-extended',
    templateUrl: './stock-in-process-update-extended.component.html'
})
export class StockInProcessUpdateExtendedComponent extends StockInProcessUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInProcessService: StockInProcessExtendedService,
        protected purchaseOrderService: PurchaseOrderService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {
        super(
            jhiAlertService,
            stockInProcessService,
            purchaseOrderService,
            commercialPurchaseOrderService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            vendorService,
            activatedRoute
        );
    }
}
