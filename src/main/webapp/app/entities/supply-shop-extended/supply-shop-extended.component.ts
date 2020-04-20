import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyShopExtendedService } from './supply-shop-extended.service';
import { SupplyShopComponent } from 'app/entities/supply-shop';

@Component({
    selector: 'jhi-supply-shop-extended',
    templateUrl: './supply-shop-extended.component.html'
})
export class SupplyShopExtendedComponent extends SupplyShopComponent {
    constructor(
        protected supplyShopService: SupplyShopExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyShopService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
