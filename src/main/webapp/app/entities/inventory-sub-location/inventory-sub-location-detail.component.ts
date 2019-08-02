import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';

@Component({
    selector: 'jhi-inventory-sub-location-detail',
    templateUrl: './inventory-sub-location-detail.component.html'
})
export class InventorySubLocationDetailComponent implements OnInit {
    inventorySubLocation: IInventorySubLocation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventorySubLocation }) => {
            this.inventorySubLocation = inventorySubLocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
