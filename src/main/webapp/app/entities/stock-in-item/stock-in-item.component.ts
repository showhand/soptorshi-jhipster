import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { StockInItemService } from './stock-in-item.service';
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

@Component({
    selector: 'jhi-stock-in-item',
    templateUrl: './stock-in-item.component.html'
})
export class StockInItemComponent implements OnInit, OnDestroy {
    stockInItems: IStockInItem[];
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
    s_ManufacturersId: number;

    constructor(
        protected stockInItemService: StockInItemService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected itemCategoryService: ItemCategoryService,
        protected itemSubCategoryService: ItemSubCategoryService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected manufacturerService: ManufacturerService
    ) {
        this.stockInItems = [];
    }

    loadAll() {
        this.stockInItemService
            .query()
            .subscribe(
                (res: HttpResponse<IStockInItem[]>) => this.paginateStockInItems(res.body, res.headers),
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
        this.manufacturerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IManufacturer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IManufacturer[]>) => response.body)
            )
            .subscribe((res: IManufacturer[]) => (this.manufacturers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getItemSubCategories() {
        this.itemSubCategoryService
            .query({
                'itemCategoriesId.equals': this.s_ItemCategoriesId
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
                'inventoryLocationsId.equals': this.s_InventoryLocationsId
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

    search() {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.loadAll();
        });
        this.registerChangeInStockInItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStockInItem) {
        return item.id;
    }

    registerChangeInStockInItems() {
        this.eventSubscriber = this.eventManager.subscribe('stockInItemListModification', response => undefined);
    }

    protected paginateStockInItems(data: IStockInItem[], headers: HttpHeaders) {
        for (let i = 0; i < data.length; i++) {
            this.stockInItems.push(data[i]);
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

    trackManufacturerById(index: number, item: IManufacturer) {
        return item.id;
    }
}
