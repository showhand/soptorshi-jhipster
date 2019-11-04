import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';
import { CommercialInvoiceDetailComponent } from 'app/entities/commercial-invoice';

@Component({
    selector: 'jhi-commercial-invoice-detail-extended',
    templateUrl: './commercial-invoice-detail-extended.component.html'
})
export class CommercialInvoiceDetailExtendedComponent extends CommercialInvoiceDetailComponent {
    commercialInvoice: ICommercialInvoice;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialInvoice }) => {
            this.commercialInvoice = commercialInvoice;
        });
    }

    previousState() {
        window.history.back();
    }
}
