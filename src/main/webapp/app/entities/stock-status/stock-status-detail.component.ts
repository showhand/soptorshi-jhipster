import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockStatus } from 'app/shared/model/stock-status.model';

@Component({
    selector: 'jhi-stock-status-detail',
    templateUrl: './stock-status-detail.component.html'
})
export class StockStatusDetailComponent implements OnInit {
    stockStatus: IStockStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockStatus }) => {
            this.stockStatus = stockStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
