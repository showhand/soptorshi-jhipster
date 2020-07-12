import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyZoneManagerExtendedService } from './supply-zone-manager-extended.service';
import { SupplyZoneManagerComponent } from 'app/entities/supply-zone-manager';

@Component({
    selector: 'jhi-supply-zone-manager-extended',
    templateUrl: './supply-zone-manager-extended.component.html'
})
export class SupplyZoneManagerExtendedComponent extends SupplyZoneManagerComponent {
    constructor(
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyZoneManagerService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
