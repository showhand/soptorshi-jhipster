import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { InventorySubLocationExtendedService } from './inventory-sub-location-extended.service';
import { InventorySubLocationComponent } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-inventory-sub-location-extended',
    templateUrl: './inventory-sub-location-extended.component.html'
})
export class InventorySubLocationExtendedComponent extends InventorySubLocationComponent {
    constructor(
        protected inventorySubLocationService: InventorySubLocationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(inventorySubLocationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
