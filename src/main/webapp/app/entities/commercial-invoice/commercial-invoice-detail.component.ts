import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';

@Component({
    selector: 'jhi-commercial-invoice-detail',
    templateUrl: './commercial-invoice-detail.component.html'
})
export class CommercialInvoiceDetailComponent implements OnInit {
    commercialInvoice: ICommercialInvoice;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialInvoice }) => {
            this.commercialInvoice = commercialInvoice;
        });
    }

    previousState() {
        window.history.back();
    }
}
