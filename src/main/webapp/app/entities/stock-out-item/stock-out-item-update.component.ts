import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import * as moment from 'moment';
import {DATE_TIME_FORMAT} from 'app/shared/constants/input.constants';
import {JhiAlertService} from 'ng-jhipster';
import {IStockOutItem} from 'app/shared/model/stock-out-item.model';
import {StockOutItemService} from './stock-out-item.service';
import {IProductCategory} from 'app/shared/model/product-category.model';
import {ProductCategoryService} from 'app/entities/product-category';
import {IProduct} from 'app/shared/model/product.model';
import {ProductService} from 'app/entities/product';
import {IInventoryLocation} from 'app/shared/model/inventory-location.model';
import {InventoryLocationService} from 'app/entities/inventory-location';
import {IInventorySubLocation} from 'app/shared/model/inventory-sub-location.model';
import {InventorySubLocationService} from 'app/entities/inventory-sub-location';
import {IStockInItem} from 'app/shared/model/stock-in-item.model';
import {StockInItemService} from 'app/entities/stock-in-item';

@Component({
    selector: 'jhi-stock-out-item-update',
    templateUrl: './stock-out-item-update.component.html'
})
export class StockOutItemUpdateComponent implements OnInit {
    stockOutItem: IStockOutItem;
    isSaving: boolean;

    productcategories: IProductCategory[];

    products: IProduct[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];

    stockinitems: IStockInItem[];
    stockOutDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockOutItemService: StockOutItemService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected stockInItemService: StockInItemService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockOutItem }) => {
            this.stockOutItem = stockOutItem;
            this.stockOutDate = this.stockOutItem.stockOutDate != null ? this.stockOutItem.stockOutDate.format(DATE_TIME_FORMAT) : null;
        });
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
        this.stockInItemService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockInItem[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockInItem[]>) => response.body)
            )
            .subscribe((res: IStockInItem[]) => (this.stockinitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.stockOutItem.stockOutDate = this.stockOutDate != null ? moment(this.stockOutDate, DATE_TIME_FORMAT) : null;
        if (this.stockOutItem.id !== undefined) {
            this.subscribeToSaveResponse(this.stockOutItemService.update(this.stockOutItem));
        } else {
            this.subscribeToSaveResponse(this.stockOutItemService.create(this.stockOutItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockOutItem>>) {
        result.subscribe((res: HttpResponse<IStockOutItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStockInItemById(index: number, item: IStockInItem) {
        return item.id;
    }
}
