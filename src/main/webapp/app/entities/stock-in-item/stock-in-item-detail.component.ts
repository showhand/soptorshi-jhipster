import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockInItem } from 'app/shared/model/stock-in-item.model';

@Component({
    selector: 'jhi-stock-in-item-detail',
    templateUrl: './stock-in-item-detail.component.html'
})
export class StockInItemDetailComponent implements OnInit {
    stockInItem: IStockInItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInItem }) => {
            this.stockInItem = stockInItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
