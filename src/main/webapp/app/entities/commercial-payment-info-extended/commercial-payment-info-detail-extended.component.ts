import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommercialPaymentInfoDetailComponent } from 'app/entities/commercial-payment-info';

@Component({
    selector: 'jhi-commercial-payment-info-detail-extended',
    templateUrl: './commercial-payment-info-detail-extended.component.html'
})
export class CommercialPaymentInfoDetailExtendedComponent extends CommercialPaymentInfoDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
