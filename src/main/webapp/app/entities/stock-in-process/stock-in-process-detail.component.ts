import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockInProcess } from 'app/shared/model/stock-in-process.model';

@Component({
    selector: 'jhi-stock-in-process-detail',
    templateUrl: './stock-in-process-detail.component.html'
})
export class StockInProcessDetailComponent implements OnInit {
    stockInProcess: IStockInProcess;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInProcess }) => {
            this.stockInProcess = stockInProcess;
        });
    }

    previousState() {
        window.history.back();
    }
}
