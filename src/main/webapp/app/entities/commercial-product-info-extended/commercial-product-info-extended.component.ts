import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';

import { CommercialProductInfoExtendedService } from './commercial-product-info-extended.service';
import { CommercialProductInfoComponent } from 'app/entities/commercial-product-info';

@Component({
    selector: 'jhi-commercial-product-info-extended',
    templateUrl: './commercial-product-info-extended.component.html'
})
export class CommercialProductInfoExtendedComponent extends CommercialProductInfoComponent {
    constructor(
        protected commercialProductInfoService: CommercialProductInfoExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialProductInfoService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
