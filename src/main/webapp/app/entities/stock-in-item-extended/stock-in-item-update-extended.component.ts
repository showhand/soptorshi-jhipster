import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { StockInItemExtendedService } from './stock-in-item-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockInProcessService } from 'app/entities/stock-in-process';
import { StockInItemUpdateComponent } from 'app/entities/stock-in-item';
import { VendorService } from 'app/entities/vendor';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-stock-in-item-update-extended',
    templateUrl: './stock-in-item-update-extended.component.html'
})
export class StockInItemUpdateExtendedComponent extends StockInItemUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInItemService: StockInItemExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected vendorService: VendorService,
        protected stockInProcessService: StockInProcessService,
        protected purchaseOrderService: PurchaseOrderService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            stockInItemService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            vendorService,
            stockInProcessService,
            purchaseOrderService,
            commercialPurchaseOrderService,
            activatedRoute
        );
    }
}
