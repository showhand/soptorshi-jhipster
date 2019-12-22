import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { StockStatusExtendedService } from './stock-status-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockStatusComponent } from 'app/entities/stock-status';

@Component({
    selector: 'jhi-stock-status-extended',
    templateUrl: './stock-status-extended.component.html'
})
export class StockStatusExtendedComponent extends StockStatusComponent {
    constructor(
        protected stockStatusService: StockStatusExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService
    ) {
        super(stockStatusService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
