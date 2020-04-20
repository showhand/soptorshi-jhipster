import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyOrderExtendedService } from './supply-order-extended.service';
import { SupplyOrderComponent } from 'app/entities/supply-order';

@Component({
    selector: 'jhi-supply-order-extended',
    templateUrl: './supply-order-extended.component.html'
})
export class SupplyOrderExtendedComponent extends SupplyOrderComponent {
    constructor(
        protected supplyOrderService: SupplyOrderExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyOrderService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
