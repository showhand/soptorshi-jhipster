import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationDetailComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-detail-extended',
    templateUrl: './inventory-location-detail-extended.component.html'
})
export class InventoryLocationDetailExtendedComponent extends InventoryLocationDetailComponent {
    inventoryLocation: IInventoryLocation;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
