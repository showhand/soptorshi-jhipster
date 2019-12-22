import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { StockOutItemExtendedService } from './stock-out-item-extended.service';
import { ItemCategoryService } from 'app/entities/item-category';
import { ItemSubCategoryService } from 'app/entities/item-sub-category';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockOutItemComponent } from 'app/entities/stock-out-item';

@Component({
    selector: 'jhi-stock-out-item-extended',
    templateUrl: './stock-out-item-extended.component.html'
})
export class StockOutItemExtendedComponent extends StockOutItemComponent {
    constructor(
        protected stockOutItemService: StockOutItemExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected itemCategoryService: ItemCategoryService,
        protected itemSubCategoryService: ItemSubCategoryService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService
    ) {
        super(stockOutItemService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
