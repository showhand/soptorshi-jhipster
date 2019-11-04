import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceDetailComponent } from 'app/entities/commercial-proforma-invoice';

@Component({
    selector: 'jhi-commercial-proforma-invoice-detail-extended',
    templateUrl: './commercial-proforma-invoice-detail-extended.component.html'
})
export class CommercialProformaInvoiceDetailExtendedComponent extends CommercialProformaInvoiceDetailComponent {
    commercialProformaInvoice: ICommercialProformaInvoice;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialProformaInvoice }) => {
            this.commercialProformaInvoice = commercialProformaInvoice;
        });
    }

    previousState() {
        window.history.back();
    }
}
