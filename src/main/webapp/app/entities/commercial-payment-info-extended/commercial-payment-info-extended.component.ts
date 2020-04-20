import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { CommercialPaymentInfoExtendedService } from './commercial-payment-info-extended.service';
import { CommercialPaymentInfoComponent } from 'app/entities/commercial-payment-info';

@Component({
    selector: 'jhi-commercial-payment-info-extended',
    templateUrl: './commercial-payment-info-extended.component.html'
})
export class CommercialPaymentInfoExtendedComponent extends CommercialPaymentInfoComponent {
    constructor(
        protected commercialPaymentInfoService: CommercialPaymentInfoExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialPaymentInfoService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
