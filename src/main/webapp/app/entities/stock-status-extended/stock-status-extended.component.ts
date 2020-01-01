import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { StockStatusExtendedService } from './stock-status-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockStatusComponent } from 'app/entities/stock-status';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IProduct } from 'app/shared/model/product.model';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { IInventorySubLocation, InventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { IStockStatus } from 'app/shared/model/stock-status.model';

@Component({
    selector: 'jhi-stock-status-extended',
    templateUrl: './stock-status-extended.component.html'
})
export class StockStatusExtendedComponent extends StockStatusComponent implements OnInit {
    productCategoryId: number;
    productId: number;
    inventoryLocationId: number;
    inventorySubLocationId: number;
    containerTrackingId: string;

    productcategories: IProductCategory[];
    products: IProduct[];
    inventorylocations: IInventoryLocation[];
    inventorysublocations: InventorySubLocation[];

    totalQuantity: number = 0;
    availableQuantity: number = 0;
    totalPrice: number = 0;
    availablePrice: number = 0;

    predicate: any;
    reverse: any;

    constructor(
        protected stockStatusService: StockStatusExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService
    ) {
        super(stockStatusService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
        this.predicate = 'stockInDate';
        this.reverse = false;
    }

    ngOnInit() {
        this.getProductCategories();
        this.getInventoryLocation();
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStockStatuses();
    }

    hunt() {
        this.stockStatuses = [];
        if (this.containerTrackingId) {
            this.stockStatusService
                .query({
                    'productCategoriesId.equals': this.productCategoryId ? this.productCategoryId : '',
                    'productsId.equals': this.productId ? this.productId : '',
                    'inventoryLocationsId.equals': this.inventoryLocationId ? this.inventoryLocationId : '',
                    'inventorySubLocationsId.equals': this.inventorySubLocationId ? this.inventorySubLocationId : '',
                    'containerTrackingId.equals': this.containerTrackingId,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) => this.paginateStockStatuses(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.stockStatusService
                .query({
                    'productCategoriesId.equals': this.productCategoryId ? this.productCategoryId : '',
                    'productsId.equals': this.productId ? this.productId : '',
                    'inventoryLocationsId.equals': this.inventoryLocationId ? this.inventoryLocationId : '',
                    'inventorySubLocationsId.equals': this.inventorySubLocationId ? this.inventorySubLocationId : '',
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) => this.paginateStockStatuses(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    calculateTotal() {
        this.totalQuantity = 0;
        this.availableQuantity = 0;
        this.availablePrice = 0;
        this.totalPrice = 0;

        if (this.stockStatuses) {
            if (this.productCategoryId && this.productId) {
                for (let i = 0; i < this.stockStatuses.length; i++) {
                    this.totalQuantity += this.stockStatuses[i].totalQuantity;
                    this.availableQuantity += this.stockStatuses[i].availableQuantity;
                    this.totalPrice += this.stockStatuses[i].totalPrice;
                    this.availablePrice += this.stockStatuses[i].availablePrice;
                }
            }
        }
    }

    getProductCategories() {
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getProducts() {
        this.productService
            .query({
                'productCategoryId.equals': this.productCategoryId ? this.productCategoryId : ''
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getInventoryLocation() {
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
    }

    getInventorySubLocation() {
        this.inventorySubLocationService
            .query({
                'inventoryLocationsId.equals': this.inventoryLocationId ? this.inventoryLocationId : ''
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

    protected paginateStockStatuses(data: IStockStatus[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.stockStatuses.push(data[i]);
        }
        this.calculateTotal();
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
