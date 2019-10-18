import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessDetailComponent } from 'app/entities/stock-in-process';

@Component({
    selector: 'jhi-stock-in-process-detail-extended',
    templateUrl: './stock-in-process-detail.component.extended.html'
})
export class StockInProcessDetailComponentExtended extends StockInProcessDetailComponent implements OnInit {
    stockInProcess: IStockInProcess;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInProcess }) => {
            this.stockInProcess = stockInProcess;
        });
    }

    previousState() {
        window.history.back();
    }
}
