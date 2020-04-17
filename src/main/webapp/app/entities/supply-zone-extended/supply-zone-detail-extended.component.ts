import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyZoneDetailComponent } from 'app/entities/supply-zone';

@Component({
    selector: 'jhi-supply-zone-detail-extended',
    templateUrl: './supply-zone-detail-extended.component.html'
})
export class SupplyZoneDetailExtendedComponent extends SupplyZoneDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
