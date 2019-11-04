import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceExtendedService } from './commercial-proforma-invoice-extended.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';
import { CommercialProformaInvoiceUpdateComponent } from 'app/entities/commercial-proforma-invoice';

@Component({
    selector: 'jhi-commercial-proforma-invoice-update-extended',
    templateUrl: './commercial-proforma-invoice-update-extended.component.html'
})
export class CommercialProformaInvoiceUpdateExtendedComponent extends CommercialProformaInvoiceUpdateComponent {
    commercialProformaInvoice: ICommercialProformaInvoice;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];
    proformaDateDp: any;
    createOnDp: any;
    updatedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialProformaInvoiceService: CommercialProformaInvoiceExtendedService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, commercialProformaInvoiceService, commercialPurchaseOrderService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialProformaInvoice }) => {
            this.commercialProformaInvoice = commercialProformaInvoice;
        });
        this.commercialPurchaseOrderService
            .query({ 'commercialProformaInvoiceId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPurchaseOrder[]) => {
                    if (!this.commercialProformaInvoice.commercialPurchaseOrderId) {
                        this.commercialpurchaseorders = res;
                    } else {
                        this.commercialPurchaseOrderService
                            .find(this.commercialProformaInvoice.commercialPurchaseOrderId)
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialProformaInvoice.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialProformaInvoiceService.update(this.commercialProformaInvoice));
        } else {
            this.subscribeToSaveResponse(this.commercialProformaInvoiceService.create(this.commercialProformaInvoice));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialProformaInvoice>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialProformaInvoice>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
}
