import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ProductionExtendedService } from './production-extended.service';
import { ProductionComponent } from 'app/entities/production';

@Component({
    selector: 'jhi-production-extended',
    templateUrl: './production-extended.component.html'
})
export class ProductionExtendedComponent extends ProductionComponent {
    constructor(
        protected productionService: ProductionExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(productionService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
