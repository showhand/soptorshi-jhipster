import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusService } from './stock-status.service';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemService } from 'app/entities/stock-in-item';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';

@Component({
    selector: 'jhi-stock-status-update',
    templateUrl: './stock-status-update.component.html'
})
export class StockStatusUpdateComponent implements OnInit {
    stockStatus: IStockStatus;
    isSaving: boolean;

    stockinitems: IStockInItem[];

    productcategories: IProductCategory[];

    products: IProduct[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];
    stockInDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockStatusService: StockStatusService,
        protected stockInItemService: StockInItemService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockStatus }) => {
            this.stockStatus = stockStatus;
            this.stockInDate = this.stockStatus.stockInDate != null ? this.stockStatus.stockInDate.format(DATE_TIME_FORMAT) : null;
        });
        this.stockInItemService
            .query({ 'stockStatusId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IStockInItem[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockInItem[]>) => response.body)
            )
            .subscribe(
                (res: IStockInItem[]) => {
                    if (!this.stockStatus.stockInItemId) {
                        this.stockinitems = res;
                    } else {
                        this.stockInItemService
                            .find(this.stockStatus.stockInItemId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IStockInItem>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IStockInItem>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IStockInItem) => (this.stockinitems = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
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
        this.inventoryLocationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInventoryLocation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInventoryLocation[]>) => response.body)
            )
            .subscribe(
                (res: IInventoryLocation[]) => (this.inventorylocations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.inventorySubLocationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IInventorySubLocation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IInventorySubLocation[]>) => response.body)
            )
            .subscribe(
                (res: IInventorySubLocation[]) => (this.inventorysublocations = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.stockStatus.stockInDate = this.stockInDate != null ? moment(this.stockInDate, DATE_TIME_FORMAT) : null;
        if (this.stockStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.stockStatusService.update(this.stockStatus));
        } else {
            this.subscribeToSaveResponse(this.stockStatusService.create(this.stockStatus));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockStatus>>) {
        result.subscribe((res: HttpResponse<IStockStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStockInItemById(index: number, item: IStockInItem) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackInventoryLocationById(index: number, item: IInventoryLocation) {
        return item.id;
    }

    trackInventorySubLocationById(index: number, item: IInventorySubLocation) {
        return item.id;
    }
}
