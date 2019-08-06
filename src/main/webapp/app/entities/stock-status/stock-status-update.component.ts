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
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from 'app/entities/item-category';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from 'app/entities/item-sub-category';
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

    itemcategories: IItemCategory[];

    itemsubcategories: IItemSubCategory[];

    inventorylocations: IInventoryLocation[];

    inventorysublocations: IInventorySubLocation[];
    stockInDate: string;
    expiryDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockStatusService: StockStatusService,
        protected itemCategoryService: ItemCategoryService,
        protected itemSubCategoryService: ItemSubCategoryService,
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
