import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InventorySubLocationDetailComponent } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-inventory-sub-location-detail-extended',
    templateUrl: './inventory-sub-location-detail-extended.component.html'
})
export class InventorySubLocationDetailExtendedComponent extends InventorySubLocationDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
