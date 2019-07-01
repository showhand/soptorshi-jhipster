import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionDetailsService } from './requisition-details.service';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ProductPriceService } from 'app/entities/product-price';
import { IProductPrice } from 'app/shared/model/product-price.model';

@Component({
    selector: 'jhi-requisition-details-update',
    templateUrl: './requisition-details-update.component.html'
})
export class RequisitionDetailsUpdateComponent implements OnInit {
    requisitionDetails: IRequisitionDetails;
    isSaving: boolean;

    productPrice: IProductPrice;
    requisitions: IRequisition[];

    products: IProduct[];
    requiredOnDp: any;
    estimatedDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionDetailsService: RequisitionDetailsService,
        protected requisitionService: RequisitionService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected productPriceService: ProductPriceService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requisitionDetails }) => {
            this.requisitionDetails = requisitionDetails;
        });
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    productSelected(productId: number) {
        this.productPriceService
            .query({
                'productId.equals': productId
            })
            .subscribe((res: HttpResponse<IProduct[]>) => {
                this.productPrice = res.body[res.body.length - 1];
                let amount = this.productPrice.price;
            });
    }

    calculateQuantity() {
        if (this.requisitionDetails.productId && this.requisitionDetails.unit) {
            const quantity = this.productPrice.price * this.requisitionDetails.unit;
            this.requisitionDetails.quantity = quantity;
        }
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.requisitionDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.requisitionDetailsService.update(this.requisitionDetails));
        } else {
            this.subscribeToSaveResponse(this.requisitionDetailsService.create(this.requisitionDetails));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisitionDetails>>) {
        result.subscribe((res: HttpResponse<IRequisitionDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
