import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { StockInItemExtendedService } from './stock-in-item-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockInItemComponent } from 'app/entities/stock-in-item';

@Component({
    selector: 'jhi-stock-in-item-extended',
    templateUrl: './stock-in-item-extended.component.html'
})
export class StockInItemExtendedComponent extends StockInItemComponent {
    constructor(
        protected stockInItemService: StockInItemExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService
    ) {
        super(stockInItemService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
