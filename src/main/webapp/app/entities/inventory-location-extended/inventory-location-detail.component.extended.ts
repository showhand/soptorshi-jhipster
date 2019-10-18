import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationDetailComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-detail-extended',
    templateUrl: './inventory-location-detail.component.extended.html'
})
export class InventoryLocationDetailComponentExtended extends InventoryLocationDetailComponent implements OnInit {
    inventoryLocation: IInventoryLocation;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventoryLocation }) => {
            this.inventoryLocation = inventoryLocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
