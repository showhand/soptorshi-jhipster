import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoDetailComponent } from 'app/entities/commercial-payment-info';

@Component({
    selector: 'jhi-commercial-payment-info-detail-extended',
    templateUrl: './commercial-payment-info-detail-extended.component.html'
})
export class CommercialPaymentInfoDetailExtendedComponent extends CommercialPaymentInfoDetailComponent {
    commercialPaymentInfo: ICommercialPaymentInfo;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPaymentInfo }) => {
            this.commercialPaymentInfo = commercialPaymentInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
