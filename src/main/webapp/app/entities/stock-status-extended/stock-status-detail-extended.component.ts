import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusDetailComponent } from 'app/entities/stock-status';

@Component({
    selector: 'jhi-stock-status-detail-extended',
    templateUrl: './stock-status-detail-extended.component.html'
})
export class StockStatusDetailExtendedComponent extends StockStatusDetailComponent implements OnInit {
    stockStatus: IStockStatus;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockStatus }) => {
            this.stockStatus = stockStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
