import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyZoneManagerDetailComponent } from 'app/entities/supply-zone-manager';

@Component({
    selector: 'jhi-supply-zone-manager-detail-extended',
    templateUrl: './supply-zone-manager-detail-extended.component.html'
})
export class SupplyZoneManagerDetailExtendedComponent extends SupplyZoneManagerDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
