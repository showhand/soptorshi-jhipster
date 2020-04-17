import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyOrderDetailComponent } from 'app/entities/supply-order';

@Component({
    selector: 'jhi-supply-order-detail-extended',
    templateUrl: './supply-order-detail-extended.component.html'
})
export class SupplyOrderDetailExtendedComponent extends SupplyOrderDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
