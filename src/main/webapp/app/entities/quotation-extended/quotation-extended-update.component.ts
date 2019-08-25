import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IQuotation } from 'app/shared/model/quotation.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { IVendor } from 'app/shared/model/vendor.model';
import { VendorService } from 'app/entities/vendor';
import { QuotationService, QuotationUpdateComponent } from 'app/entities/quotation';

@Component({
    selector: 'jhi-quotation-extended-update',
    templateUrl: './quotation-extended-update.component.html'
})
export class QuotationExtendedUpdateComponent extends QuotationUpdateComponent implements OnInit {
    quotation: IQuotation;
    isSaving: boolean;

    requisitions: IRequisition[];

    vendors: IVendor[];
    modifiedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected quotationService: QuotationService,
        protected requisitionService: RequisitionService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, quotationService, requisitionService, vendorService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quotation }) => {
            this.quotation = quotation;
        });
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.vendorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVendor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVendor[]>) => response.body)
            )
            .subscribe((res: IVendor[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }
}
