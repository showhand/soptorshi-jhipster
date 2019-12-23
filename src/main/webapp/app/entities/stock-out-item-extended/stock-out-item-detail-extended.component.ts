import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { StockOutItemDetailComponent } from 'app/entities/stock-out-item';

@Component({
    selector: 'jhi-stock-out-item-detail-extended',
    templateUrl: './stock-out-item-detail-extended.component.html'
})
export class StockOutItemDetailExtendedComponent extends StockOutItemDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
