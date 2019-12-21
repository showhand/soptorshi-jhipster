import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { StockInProcessExtendedService } from './stock-in-process-extended.service';
import { StockInProcessComponent } from 'app/entities/stock-in-process';

@Component({
    selector: 'jhi-stock-in-process-extended',
    templateUrl: './stock-in-process-extended.component.html'
})
export class StockInProcessExtendedComponent extends StockInProcessComponent {
    constructor(
        protected stockInProcessService: StockInProcessExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(stockInProcessService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
