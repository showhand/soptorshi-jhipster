import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';
import { CommercialInvoiceService } from './commercial-invoice.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';
import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceService } from 'app/entities/commercial-proforma-invoice';
import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingService } from 'app/entities/commercial-packaging';

@Component({
    selector: 'jhi-commercial-invoice-update',
    templateUrl: './commercial-invoice-update.component.html'
})
export class CommercialInvoiceUpdateComponent implements OnInit {
    commercialInvoice: ICommercialInvoice;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];

    commercialproformainvoices: ICommercialProformaInvoice[];

    commercialpackagings: ICommercialPackaging[];
    createOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialInvoiceService: CommercialInvoiceService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected commercialProformaInvoiceService: CommercialProformaInvoiceService,
        protected commercialPackagingService: CommercialPackagingService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialInvoice }) => {
            this.commercialInvoice = commercialInvoice;
        });
        this.commercialPurchaseOrderService
            .query({ 'commercialInvoiceId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPurchaseOrder[]) => {
                    if (!this.commercialInvoice.commercialPurchaseOrderId) {
                        this.commercialpurchaseorders = res;
                    } else {
                        this.commercialPurchaseOrderService
                            .find(this.commercialInvoice.commercialPurchaseOrderId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialPurchaseOrder>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialPurchaseOrder>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialPurchaseOrder) => (this.commercialpurchaseorders = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.commercialProformaInvoiceService
            .query({ 'commercialInvoiceId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialProformaInvoice[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialProformaInvoice[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialProformaInvoice[]) => {
                    if (!this.commercialInvoice.commercialProformaInvoiceId) {
                        this.commercialproformainvoices = res;
                    } else {
                        this.commercialProformaInvoiceService
                            .find(this.commercialInvoice.commercialProformaInvoiceId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialProformaInvoice>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialProformaInvoice>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialProformaInvoice) => (this.commercialproformainvoices = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.commercialPackagingService
            .query({ 'commercialInvoiceId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPackaging[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPackaging[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPackaging[]) => {
                    if (!this.commercialInvoice.commercialPackagingId) {
                        this.commercialpackagings = res;
                    } else {
                        this.commercialPackagingService
                            .find(this.commercialInvoice.commercialPackagingId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialPackaging>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialPackaging>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialPackaging) => (this.commercialpackagings = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialInvoice.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialInvoiceService.update(this.commercialInvoice));
        } else {
            this.subscribeToSaveResponse(this.commercialInvoiceService.create(this.commercialInvoice));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialInvoice>>) {
        result.subscribe((res: HttpResponse<ICommercialInvoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommercialPurchaseOrderById(index: number, item: ICommercialPurchaseOrder) {
        return item.id;
    }

    trackCommercialProformaInvoiceById(index: number, item: ICommercialProformaInvoice) {
        return item.id;
    }

    trackCommercialPackagingById(index: number, item: ICommercialPackaging) {
        return item.id;
    }
}
