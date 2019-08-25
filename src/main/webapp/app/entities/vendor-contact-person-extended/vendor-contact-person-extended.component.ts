import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { IVendor } from 'app/shared/model/vendor.model';
import { VendorContactPersonComponent, VendorContactPersonService } from 'app/entities/vendor-contact-person';

@Component({
    selector: 'jhi-vendor-contact-person-extended',
    templateUrl: './vendor-contact-person-extended.component.html'
})
export class VendorContactPersonExtendedComponent extends VendorContactPersonComponent implements OnInit, OnDestroy {
    vendorContactPerson: IVendorContactPerson;

    constructor(
        protected vendorContactPersonService: VendorContactPersonService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(vendorContactPersonService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }

    loadAll() {
        if (this.currentSearch) {
            this.vendorContactPersonService
                .query({
                    'vendorId.equals': this.vendorContactPerson.vendorId,
                    page: this.page - 1,
                    'name.includes': this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IVendorContactPerson[]>) => this.paginateVendorContactPeople(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.vendorContactPersonService
            .query({
                'vendorId.equals': this.vendorContactPerson.vendorId,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IVendorContactPerson[]>) => this.paginateVendorContactPeople(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    back() {
        window.history.back();
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vendorContactPerson }) => {
            this.vendorContactPerson = vendorContactPerson;
            this.loadAll();
        });
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVendorContactPeople();
    }
}
