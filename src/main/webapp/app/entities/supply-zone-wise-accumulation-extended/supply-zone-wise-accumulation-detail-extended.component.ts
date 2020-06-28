import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyZoneWiseAccumulationDetailComponent } from 'app/entities/supply-zone-wise-accumulation';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-detail-extended',
    templateUrl: './supply-zone-wise-accumulation-detail-extended.component.html'
})
export class SupplyZoneWiseAccumulationDetailExtendedComponent extends SupplyZoneWiseAccumulationDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
