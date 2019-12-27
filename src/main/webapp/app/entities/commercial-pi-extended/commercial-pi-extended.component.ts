import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { CommercialPiExtendedService } from './commercial-pi-extended.service';
import { CommercialPiComponent } from 'app/entities/commercial-pi';

@Component({
    selector: 'jhi-commercial-pi-extended',
    templateUrl: './commercial-pi-extended.component.html'
})
export class CommercialPiExtendedComponent extends CommercialPiComponent {
    constructor(
        protected commercialPiService: CommercialPiExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialPiService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
