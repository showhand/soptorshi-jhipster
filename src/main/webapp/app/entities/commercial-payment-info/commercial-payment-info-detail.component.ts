import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';

@Component({
    selector: 'jhi-commercial-payment-info-detail',
    templateUrl: './commercial-payment-info-detail.component.html'
})
export class CommercialPaymentInfoDetailComponent implements OnInit {
    commercialPaymentInfo: ICommercialPaymentInfo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPaymentInfo }) => {
            this.commercialPaymentInfo = commercialPaymentInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
