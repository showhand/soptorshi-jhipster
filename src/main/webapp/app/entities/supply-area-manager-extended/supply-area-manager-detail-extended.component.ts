import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyAreaManagerDetailComponent } from 'app/entities/supply-area-manager';

@Component({
    selector: 'jhi-supply-area-manager-detail-extended',
    templateUrl: './supply-area-manager-detail-extended.component.html'
})
export class SupplyAreaManagerDetailExtendedComponent extends SupplyAreaManagerDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
