import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IVendor } from 'app/shared/model/vendor.model';

@Component({
    selector: 'jhi-vendor-detail',
    templateUrl: './vendor-detail.component.html'
})
export class VendorDetailComponent implements OnInit {
    vendor: IVendor;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vendor }) => {
            this.vendor = vendor;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
