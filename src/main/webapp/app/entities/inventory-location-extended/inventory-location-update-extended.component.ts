import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InventoryLocationExtendedService } from './inventory-location-extended.service';
import { InventoryLocationUpdateComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-update-extended',
    templateUrl: './inventory-location-update-extended.component.html'
})
export class InventoryLocationUpdateExtendedComponent extends InventoryLocationUpdateComponent {
    constructor(protected inventoryLocationService: InventoryLocationExtendedService, protected activatedRoute: ActivatedRoute) {
        super(inventoryLocationService, activatedRoute);
    }
}
