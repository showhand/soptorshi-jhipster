import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { VendorContactPersonService } from './vendor-contact-person.service';
import { IVendor } from 'app/shared/model/vendor.model';
import { VendorService } from 'app/entities/vendor';

@Component({
    selector: 'jhi-vendor-contact-person-update',
    templateUrl: './vendor-contact-person-update.component.html'
})
export class VendorContactPersonUpdateComponent implements OnInit {
    vendorContactPerson: IVendorContactPerson;
    isSaving: boolean;

    vendors: IVendor[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected vendorContactPersonService: VendorContactPersonService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vendorContactPerson }) => {
            this.vendorContactPerson = vendorContactPerson;
        });
        this.vendorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVendor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVendor[]>) => response.body)
            )
            .subscribe((res: IVendor[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vendorContactPerson.id !== undefined) {
            this.subscribeToSaveResponse(this.vendorContactPersonService.update(this.vendorContactPerson));
        } else {
            this.subscribeToSaveResponse(this.vendorContactPersonService.create(this.vendorContactPerson));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVendorContactPerson>>) {
        result.subscribe((res: HttpResponse<IVendorContactPerson>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackVendorById(index: number, item: IVendor) {
        return item.id;
    }
}
