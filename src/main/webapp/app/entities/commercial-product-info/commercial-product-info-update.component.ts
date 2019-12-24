import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { CommercialProductInfoService } from './commercial-product-info.service';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { CommercialBudgetService } from 'app/entities/commercial-budget';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-commercial-product-info-update',
    templateUrl: './commercial-product-info-update.component.html'
})
export class CommercialProductInfoUpdateComponent implements OnInit {
    commercialProductInfo: ICommercialProductInfo;
    isSaving: boolean;

    commercialbudgets: ICommercialBudget[];

    productcategories: IProductCategory[];

    products: IProduct[];
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialProductInfoService: CommercialProductInfoService,
        protected commercialBudgetService: CommercialBudgetService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialProductInfo }) => {
            this.commercialProductInfo = commercialProductInfo;
            this.createdOn =
                this.commercialProductInfo.createdOn != null ? this.commercialProductInfo.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn =
                this.commercialProductInfo.updatedOn != null ? this.commercialProductInfo.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.commercialBudgetService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialBudget[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialBudget[]>) => response.body)
            )
            .subscribe((res: ICommercialBudget[]) => (this.commercialbudgets = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.commercialProductInfo.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.commercialProductInfo.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.commercialProductInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialProductInfoService.update(this.commercialProductInfo));
        } else {
            this.subscribeToSaveResponse(this.commercialProductInfoService.create(this.commercialProductInfo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialProductInfo>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialProductInfo>) => this.onSaveSuccess(),
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

    trackCommercialBudgetById(index: number, item: ICommercialBudget) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
