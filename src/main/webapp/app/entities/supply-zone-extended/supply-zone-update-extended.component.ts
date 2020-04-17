import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyZoneExtendedService } from './supply-zone-extended.service';
import { SupplyZoneUpdateComponent } from 'app/entities/supply-zone';

@Component({
    selector: 'jhi-supply-zone-update-extended',
    templateUrl: './supply-zone-update-extended.component.html'
})
export class SupplyZoneUpdateExtendedComponent extends SupplyZoneUpdateComponent {
    constructor(protected supplyZoneService: SupplyZoneExtendedService, protected activatedRoute: ActivatedRoute) {
        super(supplyZoneService, activatedRoute);
    }
}
