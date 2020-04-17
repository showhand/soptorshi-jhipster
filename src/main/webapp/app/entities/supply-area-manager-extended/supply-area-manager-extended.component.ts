import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyAreaManagerExtendedService } from './supply-area-manager-extended.service';
import { SupplyAreaManagerComponent } from 'app/entities/supply-area-manager';

@Component({
    selector: 'jhi-supply-area-manager-extended',
    templateUrl: './supply-area-manager-extended.component.html'
})
export class SupplyAreaManagerExtendedComponent extends SupplyAreaManagerComponent {
    constructor(
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyAreaManagerService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
