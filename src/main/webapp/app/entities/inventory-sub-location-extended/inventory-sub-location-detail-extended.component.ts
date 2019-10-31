import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationDetailComponent } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-inventory-sub-location-detail-extended',
    templateUrl: './inventory-sub-location-detail-extended.component.html'
})
export class InventorySubLocationDetailExtendedComponent extends InventorySubLocationDetailComponent implements OnInit {
    inventorySubLocation: IInventorySubLocation;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventorySubLocation }) => {
            this.inventorySubLocation = inventorySubLocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
