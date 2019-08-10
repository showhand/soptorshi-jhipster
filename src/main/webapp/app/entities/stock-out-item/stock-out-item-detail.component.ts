import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockOutItem } from 'app/shared/model/stock-out-item.model';

@Component({
    selector: 'jhi-stock-out-item-detail',
    templateUrl: './stock-out-item-detail.component.html'
})
export class StockOutItemDetailComponent implements OnInit {
    stockOutItem: IStockOutItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockOutItem }) => {
            this.stockOutItem = stockOutItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
