import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyAreaExtendedService } from './supply-area-extended.service';
import { SupplyAreaComponent } from 'app/entities/supply-area';

@Component({
    selector: 'jhi-supply-area-extended',
    templateUrl: './supply-area-extended.component.html'
})
export class SupplyAreaExtendedComponent extends SupplyAreaComponent {
    constructor(
        protected supplyAreaService: SupplyAreaExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyAreaService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
