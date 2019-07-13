import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';

@Component({
    selector: 'jhi-vendor-contact-person-detail',
    templateUrl: './vendor-contact-person-detail.component.html'
})
export class VendorContactPersonDetailComponent implements OnInit {
    vendorContactPerson: IVendorContactPerson;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vendorContactPerson }) => {
            this.vendorContactPerson = vendorContactPerson;
        });
    }

    previousState() {
        window.history.back();
    }
}
