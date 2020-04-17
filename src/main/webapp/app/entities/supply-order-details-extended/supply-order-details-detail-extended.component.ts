import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyOrderDetailsDetailComponent } from 'app/entities/supply-order-details';

@Component({
    selector: 'jhi-supply-order-details-detail-extended',
    templateUrl: './supply-order-details-detail-extended.component.html'
})
export class SupplyOrderDetailsDetailExtendedComponent extends SupplyOrderDetailsDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
