import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessExtendedService } from './stock-in-process-extended.service';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from 'app/entities/item-category';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from 'app/entities/item-sub-category';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from 'app/entities/manufacturer';
import { StockInProcessUpdateComponent } from 'app/entities/stock-in-process';
import { VendorService } from 'app/entities/vendor';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';

@Component({
    selector: 'jhi-stock-in-process-update-extended',
    templateUrl: './stock-in-process-update-extended.component.html'
})
export class StockInProcessUpdateExtendedComponent extends StockInProcessUpdateComponent implements OnInit {
    stockInProcess: IStockInProcess;
    isSaving: boolean;

    itemcategories: IItemCategory[];

    itemsubcategories: IItemSubCategory[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];

    manufacturers: IManufacturer[];
    expiryDateDp: any;
    stockInDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInProcessService: StockInProcessExtendedService,
        protected purchaseOrderService: PurchaseOrderService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {
        super(
            jhiAlertService,
            stockInProcessService,
            purchaseOrderService,
            commercialPurchaseOrderService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            vendorService,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockInProcess }) => {
            this.stockInProcess = stockInProcess;
            this.stockInDate = this.stockInProcess.stockInDate != null ? this.stockInProcess.stockInDate.format(DATE_TIME_FORMAT) : null;
        });
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItemCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemCategory[]>) => response.body)
            )
            .subscribe((res: IItemCategory[]) => (this.itemcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItemSubCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemSubCategory[]>) => response.body)
            )
            .subscribe((res: IItemSubCategory[]) => (this.itemsubcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
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
                filter((mayBeOk: HttpResponse<IManufacturer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IManufacturer[]>) => response.body)
            )
            .subscribe((res: IManufacturer[]) => (this.manufacturers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getItemSubCategories() {
        this.productService
            .query({
                'itemCategoriesId.equals': this.stockInProcess.productCategoriesId
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IItemSubCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemSubCategory[]>) => response.body)
            )
            .subscribe((res: IItemSubCategory[]) => (this.itemsubcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getInventorySubLocation() {
        this.inventorySubLocationService
            .query({
                'inventoryLocationsId.equals': this.stockInProcess.inventoryLocationsId
            })
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
        if (this.validateRequest()) {
            this.stockInProcess.stockInDate = this.stockInDate != null ? moment(this.stockInDate, DATE_TIME_FORMAT) : null;
            if (this.stockInProcess.id !== undefined) {
                this.subscribeToSaveResponse(this.stockInProcessService.update(this.stockInProcess));
            } else {
                this.subscribeToSaveResponse(this.stockInProcessService.create(this.stockInProcess));
            }
        } else {
            this.isSaving = false;
        }
    }

    validateRequest(): boolean {
        const numberOfContainerTrackingId: string[] = this.stockInProcess.containerTrackingId.split(',');
        const numberOfQuantityPerContainer: string[] = this.stockInProcess.quantityPerContainer.split(',');

        if (
            this.stockInProcess.totalContainer === numberOfContainerTrackingId.length &&
            this.stockInProcess.totalContainer === numberOfQuantityPerContainer.length
        ) {
            return true;
        }

        this.onError(
            'Total number of container should be equal to number of total container tracking Id & number of total quantity per container'
        );
        return false;
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockInProcess>>) {
        result.subscribe((res: HttpResponse<IStockInProcess>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        /*this.previousState();*/
        this.router.navigate(['/stock-in-item']);
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackItemCategoryById(index: number, item: IItemCategory) {
        return item.id;
    }

    trackItemSubCategoryById(index: number, item: IItemSubCategory) {
        return item.id;
    }

    trackInventoryLocationById(index: number, item: IInventoryLocation) {
        return item.id;
    }

    trackInventorySubLocationById(index: number, item: IInventorySubLocation) {
        return item.id;
    }

    trackManufacturerById(index: number, item: IManufacturer) {
        return item.id;
    }
}
