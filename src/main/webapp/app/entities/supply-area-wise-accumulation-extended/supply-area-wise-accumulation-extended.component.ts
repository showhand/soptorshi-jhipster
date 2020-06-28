import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyAreaWiseAccumulationExtendedService } from './supply-area-wise-accumulation-extended.service';
import { SupplyAreaWiseAccumulationComponent } from 'app/entities/supply-area-wise-accumulation';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-extended',
    templateUrl: './supply-area-wise-accumulation-extended.component.html'
})
export class SupplyAreaWiseAccumulationExtendedComponent extends SupplyAreaWiseAccumulationComponent {
    constructor(
        protected supplyAreaWiseAccumulationService: SupplyAreaWiseAccumulationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyAreaWiseAccumulationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
