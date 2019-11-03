import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';

@Component({
    selector: 'jhi-commercial-proforma-invoice-detail',
    templateUrl: './commercial-proforma-invoice-detail.component.html'
})
export class CommercialProformaInvoiceDetailComponent implements OnInit {
    commercialProformaInvoice: ICommercialProformaInvoice;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialProformaInvoice }) => {
            this.commercialProformaInvoice = commercialProformaInvoice;
        });
    }

    previousState() {
        window.history.back();
    }
}
