import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { InventoryLocationExtendedService } from './inventory-location-extended.service';
import { InventoryLocationComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-extended',
    templateUrl: './inventory-location-extended.component.html'
})
export class InventoryLocationExtendedComponent extends InventoryLocationComponent {
    constructor(
        protected inventoryLocationService: InventoryLocationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(inventoryLocationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
