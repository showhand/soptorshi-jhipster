import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyOrderDetailsExtendedService } from 'app/entities/supply-order-details-extended/supply-order-details-extended.service';
import { SupplyOrderDetailsComponent } from 'app/entities/supply-order-details';

@Component({
    selector: 'jhi-supply-order-details-extended',
    templateUrl: './supply-order-details-extended.component.html'
})
export class SupplyOrderDetailsExtendedComponent extends SupplyOrderDetailsComponent {
    constructor(
        protected supplyOrderDetailsService: SupplyOrderDetailsExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyOrderDetailsService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
