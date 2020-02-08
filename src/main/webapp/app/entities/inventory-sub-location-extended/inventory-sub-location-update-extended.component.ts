import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { InventorySubLocationExtendedService } from './inventory-sub-location-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationUpdateComponent } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-inventory-sub-location-update-extended',
    templateUrl: './inventory-sub-location-update-extended.component.html'
})
export class InventorySubLocationUpdateExtendedComponent extends InventorySubLocationUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected inventorySubLocationService: InventorySubLocationExtendedService,
        protected inventoryLocationService: InventoryLocationService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, inventorySubLocationService, inventoryLocationService, activatedRoute);
    }
}
