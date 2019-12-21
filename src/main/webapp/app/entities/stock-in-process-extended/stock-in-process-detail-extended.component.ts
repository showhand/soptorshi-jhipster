import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessDetailComponent } from 'app/entities/stock-in-process';

@Component({
    selector: 'jhi-stock-in-process-detail-extended',
    templateUrl: './stock-in-process-detail-extended.component.html'
})
export class StockInProcessDetailExtendedComponent extends StockInProcessDetailComponent {
    stockInProcess: IStockInProcess;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
