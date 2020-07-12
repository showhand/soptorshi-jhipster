import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyAreaWiseAccumulationDetailComponent } from 'app/entities/supply-area-wise-accumulation';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-detail-extended',
    templateUrl: './supply-area-wise-accumulation-detail-extended.component.html'
})
export class SupplyAreaWiseAccumulationDetailExtendedComponent extends SupplyAreaWiseAccumulationDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
