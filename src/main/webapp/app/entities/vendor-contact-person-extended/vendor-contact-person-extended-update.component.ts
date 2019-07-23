import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { IVendor } from 'app/shared/model/vendor.model';
import { VendorService } from 'app/entities/vendor';
import { VendorContactPersonService, VendorContactPersonUpdateComponent } from 'app/entities/vendor-contact-person';

@Component({
    selector: 'jhi-vendor-contact-person-extended-update',
    templateUrl: './vendor-contact-person-extended-update.component.html'
})
export class VendorContactPersonExtendedUpdateComponent extends VendorContactPersonUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vendorContactPersonService: VendorContactPersonService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, vendorContactPersonService, vendorService, activatedRoute);
    }
}
