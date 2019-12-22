import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { StockOutItemExtendedService } from './stock-out-item-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockInItemService } from 'app/entities/stock-in-item';
import { StockStatusService } from 'app/entities/stock-status';
import { StockOutItemUpdateComponent } from 'app/entities/stock-out-item';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-stock-out-item-update-extended',
    templateUrl: './stock-out-item-update-extended.component.html'
})
export class StockOutItemUpdateExtendedComponent extends StockOutItemUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockOutItemService: StockOutItemExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected stockInItemService: StockInItemService,
        protected stockStatusService: StockStatusService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            stockOutItemService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            stockInItemService,
            stockStatusService,
            activatedRoute
        );
    }
}
