import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IVendor } from 'app/shared/model/vendor.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { VendorComponent, VendorService } from 'app/entities/vendor';

@Component({
    selector: 'jhi-vendor-extended',
    templateUrl: './vendor-extended.component.html'
})
export class VendorExtendedComponent extends VendorComponent {
    constructor(
        protected vendorService: VendorService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(vendorService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }
}
