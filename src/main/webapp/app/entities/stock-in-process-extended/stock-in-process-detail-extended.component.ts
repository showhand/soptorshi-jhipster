import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { StockInProcessDetailComponent } from 'app/entities/stock-in-process';

@Component({
    selector: 'jhi-stock-in-process-detail-extended',
    templateUrl: './stock-in-process-detail-extended.component.html'
})
export class StockInProcessDetailExtendedComponent extends StockInProcessDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
