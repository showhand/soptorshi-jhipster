import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { StockStatusExtendedService } from './stock-status-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockStatusUpdateComponent } from 'app/entities/stock-status';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { StockInItemExtendedService } from 'app/entities/stock-in-item-extended';

@Component({
    selector: 'jhi-stock-status-update-extended',
    templateUrl: './stock-status-update-extended.component.html'
})
export class StockStatusUpdateExtendedComponent extends StockStatusUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockStatusService: StockStatusExtendedService,
        protected stockInItemService: StockInItemExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            stockStatusService,
            stockInItemService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            activatedRoute
        );
    }
}
