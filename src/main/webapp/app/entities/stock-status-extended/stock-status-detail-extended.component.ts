import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { StockStatusDetailComponent } from 'app/entities/stock-status';

@Component({
    selector: 'jhi-stock-status-detail-extended',
    templateUrl: './stock-status-detail-extended.component.html'
})
export class StockStatusDetailExtendedComponent extends StockStatusDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
