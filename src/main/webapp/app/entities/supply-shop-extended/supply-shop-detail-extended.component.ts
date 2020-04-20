import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyShopDetailComponent } from 'app/entities/supply-shop';

@Component({
    selector: 'jhi-supply-shop-detail-extended',
    templateUrl: './supply-shop-detail-extended.component.html'
})
export class SupplyShopDetailExtendedComponent extends SupplyShopDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
