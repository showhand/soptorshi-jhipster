import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';

import { CommercialPoExtendedService } from './commercial-po-extended.service';
import { CommercialPoComponent } from 'app/entities/commercial-po';

@Component({
    selector: 'jhi-commercial-po-extended',
    templateUrl: './commercial-po-extended.component.html'
})
export class CommercialPoExtendedComponent extends CommercialPoComponent {
    constructor(
        protected commercialPoService: CommercialPoExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialPoService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
