import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiAlertService} from 'ng-jhipster';
import {StockOutItemExtendedService} from './stock-out-item-extended.service';
import {InventoryLocationService} from 'app/entities/inventory-location';
import {InventorySubLocationService} from 'app/entities/inventory-sub-location';
import {StockOutItemUpdateComponent} from 'app/entities/stock-out-item';
import {ProductCategoryService} from 'app/entities/product-category';
import {ProductService} from 'app/entities/product';
import {filter, map} from 'rxjs/operators';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {IProduct} from 'app/shared/model/product.model';
import {IInventorySubLocation} from 'app/shared/model/inventory-sub-location.model';
import {IStockStatus} from 'app/shared/model/stock-status.model';
import {IInventoryLocation} from 'app/shared/model/inventory-location.model';
import {StockInItemExtendedService} from 'app/entities/stock-in-item-extended';
import {StockStatusExtendedService} from 'app/entities/stock-status-extended';

@Component({
    selector: 'jhi-stock-out-item-update-extended',
    templateUrl: './stock-out-item-update-extended.component.html'
})
export class StockOutItemUpdateExtendedComponent extends StockOutItemUpdateComponent {
    containers: IStockStatus[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockOutItemService: StockOutItemExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected stockInItemService: StockInItemExtendedService,
        protected stockStatusService: StockStatusExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            stockOutItemService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            stockInItemService,
            activatedRoute
        );
    }

    getProducts() {
        this.productService
            .query({
                'productCategoryId.equals': this.stockOutItem.productCategoriesId ? this.stockOutItem.productCategoriesId : ''
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getInventoryLocation() {
        if (this.stockOutItem.productCategoriesId && this.stockOutItem.productsId) {
            this.stockStatusService
                .query({
                    'productCategoriesId.equals': this.stockOutItem.productCategoriesId,
                    'productsId.equals': this.stockOutItem.productsId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<IStockStatus[]>) => mayBeOk.ok),
                    map((response: HttpResponse<IStockStatus[]>) => response.body)
                )
                .subscribe(
                    (res: IStockStatus[]) => this.extractInventoryLocation(res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    getInventorySubLocation() {
        this.inventorySubLocationService
            .query({
                'inventoryLocationsId.equals': this.stockOutItem.inventoryLocationsId ? this.stockOutItem.inventoryLocationsId : ''
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

    getContainerTrackingId() {
        if (
            this.stockOutItem.productCategoriesId &&
            this.stockOutItem.productsId &&
            this.stockOutItem.inventoryLocationsId &&
            this.stockOutItem.inventorySubLocationsId
        ) {
            this.containers = [];
            this.stockStatusService
                .query({
                    'productCategoriesId.equals': this.stockOutItem.productCategoriesId,
                    'productsId.equals': this.stockOutItem.productsId,
                    'inventoryLocationsId.equals': this.stockOutItem.inventoryLocationsId,
                    'inventorySubLocationsId.equals': this.stockOutItem.inventorySubLocationsId
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) =>
                        res.body.forEach(value => {
                            this.containers.push(value);
                        }),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    getRemainingQuantity() {
        if (
            this.stockOutItem.productCategoriesId &&
            this.stockOutItem.productsId &&
            this.stockOutItem.inventoryLocationsId &&
            this.stockOutItem.inventorySubLocationsId &&
            this.stockOutItem.containerTrackingId
        ) {
            this.stockStatusService
                .query({
                    'productCategoriesId.equals': this.stockOutItem.productCategoriesId,
                    'productsId.equals': this.stockOutItem.productsId,
                    'inventoryLocationsId.equals': this.stockOutItem.inventoryLocationsId,
                    'inventorySubLocationsId.equals': this.stockOutItem.inventorySubLocationsId,
                    'containerTrackingId.equals': this.stockOutItem.containerTrackingId
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) => (this.stockOutItem.quantity = res.body[0].availableQuantity),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    extractInventoryLocation(res: IStockStatus[]) {
        this.inventorylocations = [];
        let ids: number[] = [];
        res.forEach(value => {
            ids.push(value.inventoryLocationsId);
        });
        if (ids.length > 0) {
            this.inventoryLocationService
                .query({
                    'id.in': [...ids]
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<IInventoryLocation[]>) => mayBeOk.ok),
                    map((response: HttpResponse<IInventoryLocation[]>) => response.body)
                )
                .subscribe(
                    (res: IInventoryLocation[]) => (this.inventorylocations = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    trackContainersById(index: number, item: IStockStatus) {
        return item.id;
    }
}
