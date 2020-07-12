import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';
import { SupplyZoneWiseAccumulationService } from './supply-zone-wise-accumulation.service';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-update',
    templateUrl: './supply-zone-wise-accumulation-update.component.html'
})
export class SupplyZoneWiseAccumulationUpdateComponent implements OnInit {
    supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation;
    isSaving: boolean;

    supplyzones: ISupplyZone[];

    supplyzonemanagers: ISupplyZoneManager[];

    productcategories: IProductCategory[];

    products: IProduct[];
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyZoneWiseAccumulationService: SupplyZoneWiseAccumulationService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyZoneWiseAccumulation }) => {
            this.supplyZoneWiseAccumulation = supplyZoneWiseAccumulation;
            this.createdOn =
                this.supplyZoneWiseAccumulation.createdOn != null
                    ? this.supplyZoneWiseAccumulation.createdOn.format(DATE_TIME_FORMAT)
                    : null;
            this.updatedOn =
                this.supplyZoneWiseAccumulation.updatedOn != null
                    ? this.supplyZoneWiseAccumulation.updatedOn.format(DATE_TIME_FORMAT)
                    : null;
        });
        this.supplyZoneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZone[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZone[]>) => response.body)
            )
            .subscribe((res: ISupplyZone[]) => (this.supplyzones = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyZoneManagerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => (this.supplyzonemanagers = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        this.supplyZoneWiseAccumulation.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyZoneWiseAccumulation.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyZoneWiseAccumulation.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyZoneWiseAccumulationService.update(this.supplyZoneWiseAccumulation));
        } else {
            this.subscribeToSaveResponse(this.supplyZoneWiseAccumulationService.create(this.supplyZoneWiseAccumulation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyZoneWiseAccumulation>>) {
        result.subscribe(
            (res: HttpResponse<ISupplyZoneWiseAccumulation>) => this.onSaveSuccess(),
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

    trackSupplyZoneById(index: number, item: ISupplyZone) {
        return item.id;
    }

    trackSupplyZoneManagerById(index: number, item: ISupplyZoneManager) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
