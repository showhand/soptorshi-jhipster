import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { StockInProcessExtendedService } from './stock-in-process-extended.service';
import { InventoryLocationService } from 'app/entities/inventory-location';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';
import { StockInProcessUpdateComponent } from 'app/entities/stock-in-process';
import { VendorService } from 'app/entities/vendor';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProduct } from 'app/shared/model/product.model';
import { filter, map } from 'rxjs/operators';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared';
import { StockInProcessStatus } from 'app/shared/model/stock-in-process.model';
import { RequisitionService } from 'app/entities/requisition';

@Component({
    selector: 'jhi-stock-in-process-update-extended',
    templateUrl: './stock-in-process-update-extended.component.html'
})
export class StockInProcessUpdateExtendedComponent extends StockInProcessUpdateComponent {
    products: IProduct[];
    inventorysublocations: IInventorySubLocation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInProcessService: StockInProcessExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected inventoryLocationService: InventoryLocationService,
        protected inventorySubLocationService: InventorySubLocationService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute,
        protected requisitionService: RequisitionService,
        protected router: Router
    ) {
        super(
            jhiAlertService,
            stockInProcessService,
            productCategoryService,
            productService,
            inventoryLocationService,
            inventorySubLocationService,
            vendorService,
            requisitionService,
            activatedRoute
        );
    }

    save() {
        this.isSaving = true;
        this.stockInProcess.processStartedOn = this.processStartedOn != null ? moment(this.processStartedOn, DATE_TIME_FORMAT) : null;
        this.stockInProcess.stockInDate = this.stockInDate != null ? moment(this.stockInDate, DATE_TIME_FORMAT) : null;
        if (this.stockInProcess.id !== undefined) {
            this.stockInProcess.status = StockInProcessStatus.COMPLETED_STOCK_IN_PROCESS;
            this.subscribeToSaveResponse(this.stockInProcessService.update(this.stockInProcess));
        } else {
            this.subscribeToSaveResponse(this.stockInProcessService.create(this.stockInProcess));
        }
    }

    getProducts() {
        this.productService
            .query({
                'productCategoryId.equals': this.stockInProcess.productCategoriesId
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
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
}
