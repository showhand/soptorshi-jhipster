import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryLocation } from 'app/shared/model/inventory-location.model';

@Component({
    selector: 'jhi-inventory-location-detail',
    templateUrl: './inventory-location-detail.component.html'
})
export class InventoryLocationDetailComponent implements OnInit {
    inventoryLocation: IInventoryLocation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventoryLocation }) => {
            this.inventoryLocation = inventoryLocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
