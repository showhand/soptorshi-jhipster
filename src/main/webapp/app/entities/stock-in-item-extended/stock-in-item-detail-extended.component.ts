import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemDetailComponent } from 'app/entities/stock-in-item';

@Component({
    selector: 'jhi-stock-in-item-detail-extended',
    templateUrl: './stock-in-item-detail-extended.component.html'
})
export class StockInItemDetailExtendedComponent extends StockInItemDetailComponent implements OnInit {
    stockInItem: IStockInItem;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInItem }) => {
            this.stockInItem = stockInItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
