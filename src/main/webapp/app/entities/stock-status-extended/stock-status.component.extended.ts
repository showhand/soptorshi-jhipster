import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IStockStatus } from 'app/shared/model/stock-status.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { StockStatusServiceExtended } from './stock-status.service.extended';
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
import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockStatusComponent } from 'app/entities/stock-status';

@Component({
    selector: 'jhi-stock-status-extended',
    templateUrl: './stock-status.component.extended.html'
})
export class StockStatusComponentExtended extends StockStatusComponent implements OnInit, OnDestroy {
    stockStatuses: IStockStatus[];
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
    totalQuantity: number;
    totalAvailableQuantity: number;
    totalPrice: number;
    totalAvailablePrice: number;

    constructor(
        protected stockStatusService: StockStatusServiceExtended,
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
        super(
            stockStatusService,
            jhiAlertService,
            eventManager,
            parseLinks,
            activatedRoute,
            accountService,
            itemCategoryService,
            itemSubCategoryService,
            inventoryLocationService,
            inventorySubLocationService,
            manufacturerService
        );
    }

    loadAll() {
        this.stockStatusService
            .query()
            .subscribe(
                (res: HttpResponse<IStockStatus[]>) => this.paginateStockStatuses(res.body, res.headers),
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
            this.stockStatusService
                .query({
                    'itemCategoriesId.equals': this.s_ItemCategoriesId ? this.s_ItemCategoriesId : '',
                    'itemSubCategoriesId.equals': this.s_ItemSubCategoriesId ? this.s_ItemSubCategoriesId : '',
                    'inventoryLocationsId.equals': this.s_InventoryLocationsId ? this.s_InventoryLocationsId : '',
                    'inventorySubLocationsId.equals': this.s_InventorySubLocationsId ? this.s_InventorySubLocationsId : '',
                    'containerTrackingId.equals': this.s_ContainerTrackingId
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) => this.paginateStockStatuses(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.stockStatusService
                .query({
                    'itemCategoriesId.equals': this.s_ItemCategoriesId ? this.s_ItemCategoriesId : '',
                    'itemSubCategoriesId.equals': this.s_ItemSubCategoriesId ? this.s_ItemSubCategoriesId : '',
                    'inventoryLocationsId.equals': this.s_InventoryLocationsId ? this.s_InventoryLocationsId : '',
                    'inventorySubLocationsId.equals': this.s_InventorySubLocationsId ? this.s_InventorySubLocationsId : ''
                })
                .subscribe(
                    (res: HttpResponse<IStockStatus[]>) => this.paginateStockStatuses(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.loadAll();
        });
        this.registerChangeInStockStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStockStatus) {
        return item.id;
    }

    registerChangeInStockStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('stockStatusListModification', response => undefined);
    }

    protected paginateStockStatuses(data: IStockStatus[], headers: HttpHeaders) {
        this.totalQuantity = 0;
        this.totalAvailableQuantity = 0;
        this.totalPrice = 0;
        this.totalAvailablePrice = 0;
        this.stockStatuses = [];
        for (let i = 0; i < data.length; i++) {
            this.stockStatuses.push(data[i]);
            this.totalQuantity += data[i].totalQuantity;
            this.totalAvailableQuantity += data[i].availableQuantity;
            this.totalPrice += data[i].totalPrice;
            this.totalAvailablePrice += data[i].availablePrice;
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
