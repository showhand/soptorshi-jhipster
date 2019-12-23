import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { StockInItemDetailComponent } from 'app/entities/stock-in-item';

@Component({
    selector: 'jhi-stock-in-item-detail-extended',
    templateUrl: './stock-in-item-detail-extended.component.html'
})
export class StockInItemDetailExtendedComponent extends StockInItemDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
