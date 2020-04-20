import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyAreaDetailComponent } from 'app/entities/supply-area';

@Component({
    selector: 'jhi-supply-area-detail-extended',
    templateUrl: './supply-area-detail-extended.component.html'
})
export class SupplyAreaDetailExtendedComponent extends SupplyAreaDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
