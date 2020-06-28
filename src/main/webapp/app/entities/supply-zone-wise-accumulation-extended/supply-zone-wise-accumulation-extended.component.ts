import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyZoneWiseAccumulationExtendedService } from './supply-zone-wise-accumulation-extended.service';
import { SupplyZoneWiseAccumulationComponent } from 'app/entities/supply-zone-wise-accumulation';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-extended',
    templateUrl: './supply-zone-wise-accumulation-extended.component.html'
})
export class SupplyZoneWiseAccumulationExtendedComponent extends SupplyZoneWiseAccumulationComponent {
    constructor(
        protected supplyZoneWiseAccumulationService: SupplyZoneWiseAccumulationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyZoneWiseAccumulationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
