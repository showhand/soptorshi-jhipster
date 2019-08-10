import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IStockOutItem } from 'app/shared/model/stock-out-item.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { StockOutItemService } from './stock-out-item.service';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ItemCategoryService } from 'app/entities/item-category';
import { ItemSubCategoryService } from 'app/entities/item-sub-category';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { ManufacturerService } from 'app/entities/manufacturer';
import { IStockStatus } from 'app/shared/model/stock-status.model';

@Component({
    selector: 'jhi-stock-out-item',
    templateUrl: './stock-out-item.component.html'
})
export class StockOutItemComponent implements OnInit, OnDestroy {
    stockOutItems: IStockOutItem[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemcategories: IItemCategory[];
    itemsubcategories: IItemSubCategory[];
    inventorylocations: IInventoryLocation[];
    inventorysublocations: IInventorySubLocation[];
    manufacturers: IManufacturer[];
    s_ItemCategoriesId: number;
    s_ItemSubCategoriesId: number;
    s_InventoryLocationsId: number;
    s_InventorySubLocationsId: number;
    s_ContainerTrackingId: string;

    constructor(
        protected stockOutItemService: StockOutItemService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected itemCategoryService: ItemCategoryService,
        protected itemSubCategoryService: ItemSubCategoryService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService
    ) {
        this.stockOutItems = [];
    }

    loadAll() {
        this.stockOutItemService
            .query()
            .subscribe(
                (res: HttpResponse<IStockOutItem[]>) => this.paginateStockOutItems(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.itemCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItemCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemCategory[]>) => response.body)
            )
            .subscribe((res: IItemCategory[]) => (this.itemcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.itemSubCategoryService
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
    }

    getItemSubCategories() {
        this.itemSubCategoryService
            .query({
                'itemCategoriesId.equals': this.s_ItemCategoriesId ? this.s_ItemCategoriesId : ''
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
                'inventoryLocationsId.equals': this.s_InventoryLocationsId ? this.s_InventoryLocationsId : ''
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

    search() {
        if (this.s_ContainerTrackingId) {
            this.stockOutItemService
                .query({
                    'itemCategoriesId.equals': this.s_ItemCategoriesId ? this.s_ItemCategoriesId : '',
                    'itemSubCategoriesId.equals': this.s_ItemSubCategoriesId ? this.s_ItemSubCategoriesId : '',
                    'inventoryLocationsId.equals': this.s_InventoryLocationsId ? this.s_InventoryLocationsId : '',
                    'inventorySubLocationsId.equals': this.s_InventorySubLocationsId ? this.s_InventorySubLocationsId : '',
                    'containerTrackingId.equals': this.s_ContainerTrackingId
                })
                .subscribe(
                    (res: HttpResponse<IStockOutItem[]>) => this.paginateStockOutItems(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.stockOutItemService
                .query({
                    'itemCategoriesId.equals': this.s_ItemCategoriesId ? this.s_ItemCategoriesId : '',
                    'itemSubCategoriesId.equals': this.s_ItemSubCategoriesId ? this.s_ItemSubCategoriesId : '',
                    'inventoryLocationsId.equals': this.s_InventoryLocationsId ? this.s_InventoryLocationsId : '',
                    'inventorySubLocationsId.equals': this.s_InventorySubLocationsId ? this.s_InventorySubLocationsId : ''
                })
                .subscribe(
                    (res: HttpResponse<IStockOutItem[]>) => this.paginateStockOutItems(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.loadAll();
        });
        this.registerChangeInStockOutItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStockOutItem) {
        return item.id;
    }

    registerChangeInStockOutItems() {
        this.eventSubscriber = this.eventManager.subscribe('stockOutItemListModification', response => undefined);
    }

    protected paginateStockOutItems(data: IStockOutItem[], headers: HttpHeaders) {
        this.stockOutItems = [];
        for (let i = 0; i < data.length; i++) {
            this.stockOutItems.push(data[i]);
        }
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
}
