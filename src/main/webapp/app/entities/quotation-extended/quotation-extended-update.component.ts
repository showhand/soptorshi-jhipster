import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
import { QuotationExtendedService } from 'app/entities/quotation-extended/quotation.service';

@Component({
    selector: 'jhi-quotation-extended-update',
    templateUrl: './quotation-extended-update.component.html'
})
export class QuotationExtendedUpdateComponent extends QuotationUpdateComponent implements OnInit {
    quotation: IQuotation;
    isSaving: boolean;
    vendors: IVendor[];
    modifiedOnDp: any;
    requisition: IRequisition;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected quotationService: QuotationService,
        protected requisitionService: RequisitionService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute,
        protected quotatinExtendedService: QuotationExtendedService,
        protected router: Router
    ) {
        super(dataUtils, jhiAlertService, quotationService, requisitionService, vendorService, activatedRoute);
    }

    ngOnInit() {
        console.log('window----------->');
        console.log(window);
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quotation }) => {
            this.quotation = quotation;
        });
        this.requisitionService
            .find(this.quotation.requisitionId)
            .subscribe((res: HttpResponse<IRequisition>) => (this.requisition = res.body));
        this.vendorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVendor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVendor[]>) => response.body)
            )
            .subscribe((res: IVendor[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    save() {
        this.isSaving = true;
        if (this.quotation.id !== undefined) {
            this.subscribeToSaveResponse(this.quotatinExtendedService.update(this.quotation));
        } else {
            this.subscribeToSaveResponse(this.quotationService.create(this.quotation));
        }
    }

    protected onSaveSuccess() {
        this.isSaving = false;

        this.router.navigate(['/quotation', this.quotation.id, 'edit']);
        this.previousState();
    }
}
