import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { SupplyOrderDetailsService } from './supply-order-details.service';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from 'app/entities/supply-order';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-supply-order-details-update',
    templateUrl: './supply-order-details-update.component.html'
})
export class SupplyOrderDetailsUpdateComponent implements OnInit {
    supplyOrderDetails: ISupplyOrderDetails;
    isSaving: boolean;

    supplyorders: ISupplyOrder[];

    productcategories: IProductCategory[];

    products: IProduct[];
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyOrderDetailsService: SupplyOrderDetailsService,
        protected supplyOrderService: SupplyOrderService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyOrderDetails }) => {
            this.supplyOrderDetails = supplyOrderDetails;
            this.createdOn = this.supplyOrderDetails.createdOn != null ? this.supplyOrderDetails.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyOrderDetails.updatedOn != null ? this.supplyOrderDetails.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.supplyOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyOrder[]>) => response.body)
            )
            .subscribe((res: ISupplyOrder[]) => (this.supplyorders = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplyOrderDetails.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyOrderDetails.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyOrderDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyOrderDetailsService.update(this.supplyOrderDetails));
        } else {
            this.subscribeToSaveResponse(this.supplyOrderDetailsService.create(this.supplyOrderDetails));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyOrderDetails>>) {
        result.subscribe((res: HttpResponse<ISupplyOrderDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSupplyOrderById(index: number, item: ISupplyOrder) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
