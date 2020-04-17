import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyZoneExtendedService } from './supply-zone-extended.service';
import { SupplyZoneComponent } from 'app/entities/supply-zone';

@Component({
    selector: 'jhi-supply-zone-extended',
    templateUrl: './supply-zone-extended.component.html'
})
export class SupplyZoneExtendedComponent extends SupplyZoneComponent {
    constructor(
        protected supplyZoneService: SupplyZoneExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyZoneService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
