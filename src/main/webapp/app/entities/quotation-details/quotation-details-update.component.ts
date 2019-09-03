import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { QuotationDetailsService } from './quotation-details.service';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionDetailsService } from 'app/entities/requisition-details';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-quotation-details-update',
    templateUrl: './quotation-details-update.component.html'
})
export class QuotationDetailsUpdateComponent implements OnInit {
    quotationDetails: IQuotationDetails;
    isSaving: boolean;

    quotations: IQuotation[];

    requisitiondetails: IRequisitionDetails[];

    products: IProduct[];
    estimatedDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected quotationDetailsService: QuotationDetailsService,
        protected quotationService: QuotationService,
        protected requisitionDetailsService: RequisitionDetailsService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quotationDetails }) => {
            this.quotationDetails = quotationDetails;
        });
        this.quotationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQuotation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQuotation[]>) => response.body)
            )
            .subscribe((res: IQuotation[]) => (this.quotations = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.requisitionDetailsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisitionDetails[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisitionDetails[]>) => response.body)
            )
            .subscribe(
                (res: IRequisitionDetails[]) => (this.requisitiondetails = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.quotationDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.quotationDetailsService.update(this.quotationDetails));
        } else {
            this.subscribeToSaveResponse(this.quotationDetailsService.create(this.quotationDetails));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuotationDetails>>) {
        result.subscribe((res: HttpResponse<IQuotationDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackQuotationById(index: number, item: IQuotation) {
        return item.id;
    }

    trackRequisitionDetailsById(index: number, item: IRequisitionDetails) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
