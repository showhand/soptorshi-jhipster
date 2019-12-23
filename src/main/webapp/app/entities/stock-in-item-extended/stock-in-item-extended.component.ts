import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { StockInItemExtendedService } from './stock-in-item-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockInItemComponent } from 'app/entities/stock-in-item';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IProduct } from 'app/shared/model/product.model';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { IInventorySubLocation, InventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { filter, map } from 'rxjs/operators';

@Component({
    selector: 'jhi-stock-in-item-extended',
    templateUrl: './stock-in-item-extended.component.html'
})
export class StockInItemExtendedComponent extends StockInItemComponent implements OnInit {
    productCategoryId: number;
    productId: number;
    inventoryLocationId: number;
    inventorySubLocationId: number;

    productcategories: IProductCategory[];
    products: IProduct[];
    inventorylocations: IInventoryLocation[];
    inventorysublocations: InventorySubLocation[];

    predicate: any;
    reverse: any;

    constructor(
        protected stockInItemService: StockInItemExtendedService,
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
        super(stockInItemService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
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
        this.registerChangeInStockInItems();
    }

    hunt() {
        this.stockInItems = [];
        this.stockInItemService
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
                (res: HttpResponse<IStockInItem[]>) => this.paginateStockInItems(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
