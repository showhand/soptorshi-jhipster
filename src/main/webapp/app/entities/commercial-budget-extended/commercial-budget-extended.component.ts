import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';

import { CommercialBudgetExtendedService } from './commercial-budget-extended.service';
import { CommercialBudgetComponent } from 'app/entities/commercial-budget';

@Component({
    selector: 'jhi-commercial-budget-extended',
    templateUrl: './commercial-budget-extended.component.html'
})
export class CommercialBudgetExtendedComponent extends CommercialBudgetComponent {
    constructor(
        protected commercialBudgetService: CommercialBudgetExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialBudgetService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    generateReport() {
        this.commercialBudgetService.generateReport();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.commercialBudgets = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }
}
