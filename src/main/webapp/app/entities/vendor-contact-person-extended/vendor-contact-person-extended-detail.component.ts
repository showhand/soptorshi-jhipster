import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { VendorContactPersonDetailComponent } from 'app/entities/vendor-contact-person';

@Component({
    selector: 'jhi-vendor-contact-person-extended-detail',
    templateUrl: './vendor-contact-person-extended-detail.component.html'
})
export class VendorContactPersonExtendedDetailComponent extends VendorContactPersonDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
