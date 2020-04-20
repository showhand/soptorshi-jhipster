import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { CommercialPaymentInfoExtendedService } from './commercial-payment-info-extended.service';
import { CommercialPiService } from 'app/entities/commercial-pi';
import { CommercialPaymentInfoUpdateComponent } from 'app/entities/commercial-payment-info';

@Component({
    selector: 'jhi-commercial-payment-info-update-extended',
    templateUrl: './commercial-payment-info-update-extended.component.html'
})
export class CommercialPaymentInfoUpdateExtendedComponent extends CommercialPaymentInfoUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPaymentInfoService: CommercialPaymentInfoExtendedService,
        protected commercialPiService: CommercialPiService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, commercialPaymentInfoService, commercialPiService, activatedRoute);
    }
}
