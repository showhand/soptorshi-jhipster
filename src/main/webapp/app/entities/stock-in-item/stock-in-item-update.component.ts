import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import * as moment from 'moment';
import {DATE_TIME_FORMAT} from 'app/shared/constants/input.constants';
import {JhiAlertService} from 'ng-jhipster';
import {IStockInItem} from 'app/shared/model/stock-in-item.model';
import {StockInItemService} from './stock-in-item.service';
import {IProductCategory} from 'app/shared/model/product-category.model';
import {ProductCategoryService} from 'app/entities/product-category';
import {IProduct} from 'app/shared/model/product.model';
import {ProductService} from 'app/entities/product';
import {IInventoryLocation} from 'app/shared/model/inventory-location.model';
import {InventoryLocationService} from 'app/entities/inventory-location';
import {IInventorySubLocation} from 'app/shared/model/inventory-sub-location.model';
import {InventorySubLocationService} from 'app/entities/inventory-sub-location';
import {IVendor} from 'app/shared/model/vendor.model';
import {VendorService} from 'app/entities/vendor';
import {IStockInProcess} from 'app/shared/model/stock-in-process.model';
import {StockInProcessService} from 'app/entities/stock-in-process';
import {IRequisition} from 'app/shared/model/requisition.model';
import {RequisitionService} from 'app/entities/requisition';

@Component({
    selector: 'jhi-stock-in-item-update',
    templateUrl: './stock-in-item-update.component.html'
})
export class StockInItemUpdateComponent implements OnInit {
    stockInItem: IStockInItem;
    isSaving: boolean;

    productcategories: IProductCategory[];

    products: IProduct[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];

    vendors: IVendor[];

    stockinprocesses: IStockInProcess[];

    requisitions: IRequisition[];
    mfgDateDp: any;
    expiryDateDp: any;
    stockInDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInItemService: StockInItemService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected vendorService: VendorService,
        protected stockInProcessService: StockInProcessService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockInItem }) => {
            this.stockInItem = stockInItem;
            this.stockInDate = this.stockInItem.stockInDate != null ? this.stockInItem.stockInDate.format(DATE_TIME_FORMAT) : null;
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
        this.vendorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVendor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVendor[]>) => response.body)
            )
            .subscribe((res: IVendor[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stockInProcessService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStockInProcess[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStockInProcess[]>) => response.body)
            )
            .subscribe((res: IStockInProcess[]) => (this.stockinprocesses = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.stockInItem.stockInDate = this.stockInDate != null ? moment(this.stockInDate, DATE_TIME_FORMAT) : null;
        if (this.stockInItem.id !== undefined) {
            this.subscribeToSaveResponse(this.stockInItemService.update(this.stockInItem));
        } else {
            this.subscribeToSaveResponse(this.stockInItemService.create(this.stockInItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockInItem>>) {
        result.subscribe((res: HttpResponse<IStockInItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVendorById(index: number, item: IVendor) {
        return item.id;
    }

    trackStockInProcessById(index: number, item: IStockInProcess) {
        return item.id;
    }

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }
}
