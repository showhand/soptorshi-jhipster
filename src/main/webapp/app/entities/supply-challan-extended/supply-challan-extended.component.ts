import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyChallanExtendedService } from './supply-challan-extended.service';
import { SupplyChallanComponent } from 'app/entities/supply-challan';

@Component({
    selector: 'jhi-supply-challan-extended',
    templateUrl: './supply-challan-extended.component.html'
})
export class SupplyChallanExtendedComponent extends SupplyChallanComponent {
    constructor(
        protected supplyChallanService: SupplyChallanExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(supplyChallanService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
