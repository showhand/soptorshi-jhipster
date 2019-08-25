import { VendorContactPersonExtendedComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended.component';
import { AfterViewChecked, Component, Inject, Input, OnDestroy, OnInit } from '@angular/core';
import { VendorContactPersonService } from 'app/entities/vendor-contact-person';
import { JhiAlertService, JhiEventManager, JhiParseLinks, JhiResolvePagingParams } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IVendorContactPerson, VendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VendorContactPersonExtendedUpdateComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended-update.component';
import { IVendor } from 'app/shared/model/vendor.model';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-vendor-contact-person-extended-directive',
    templateUrl: './vendor-contact-person-extended-directive.component.html'
})
export class VendorContactPersonExtendedDirectiveComponent extends VendorContactPersonExtendedComponent implements OnInit, OnDestroy {
    vendorContactPerson: IVendorContactPerson;
    @Input()
    vendorId: number;

    constructor(
        protected vendorContactPersonService: VendorContactPersonService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected modalService: NgbModal
    ) {
        super(vendorContactPersonService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);

        this.page = 1;
        this.previousPage = 1;
        this.reverse = 'ASC';
        this.predicate = 'id';
        this.itemsPerPage = 100;
    }

    ngOnInit() {
        this.vendorContactPerson = new VendorContactPerson();
        this.vendorContactPerson.vendorId = this.vendorId;
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVendorContactPeople();
    }

    deleteContactPerson(vendorContactPerson: IVendorContactPerson) {
        this.vendorContactPersonService.delete(vendorContactPerson.id).subscribe((response: HttpResponse<any>) => this.loadAll());
    }
}
