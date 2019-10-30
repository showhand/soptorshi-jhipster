import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockOutItem } from 'app/shared/model/stock-out-item.model';
import { StockOutItemDetailComponent } from 'app/entities/stock-out-item';

@Component({
    selector: 'jhi-stock-out-item-detail-extended',
    templateUrl: './stock-out-item-detail-extended.component.html'
})
export class StockOutItemDetailExtendedComponent extends StockOutItemDetailComponent implements OnInit {
    stockOutItem: IStockOutItem;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockOutItem }) => {
            this.stockOutItem = stockOutItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
