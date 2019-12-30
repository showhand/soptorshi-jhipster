import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { StockInProcessExtendedService } from './stock-in-process-extended.service';
import { StockInProcessComponent } from 'app/entities/stock-in-process';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IStockInProcess } from 'app/shared/model/stock-in-process.model';

@Component({
    selector: 'jhi-stock-in-process-extended',
    templateUrl: './stock-in-process-extended.component.html'
})
export class StockInProcessExtendedComponent extends StockInProcessComponent {
    predicate: any;
    reverse: any;
    status: string;

    constructor(
        protected stockInProcessService: StockInProcessExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(stockInProcessService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
        this.predicate = 'processStartedOn';
        this.reverse = false;
    }

    hunt() {
        this.stockInProcesses = [];
        this.stockInProcessService
            .query({
                'status.equals': this.status,
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IStockInProcess[]>) => this.paginateStockInProcesses(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
