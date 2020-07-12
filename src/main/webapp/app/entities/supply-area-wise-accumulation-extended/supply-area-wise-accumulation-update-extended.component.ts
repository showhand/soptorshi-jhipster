import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyAreaWiseAccumulationExtendedService } from './supply-area-wise-accumulation-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { SupplyAreaService } from 'app/entities/supply-area';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { SupplyAreaWiseAccumulationUpdateComponent } from 'app/entities/supply-area-wise-accumulation';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplyAreaManager, SupplyAreaManagerStatus } from 'app/shared/model/supply-area-manager.model';
import { ISupplyOrder, SupplyOrder, SupplyOrderStatus } from 'app/shared/model/supply-order.model';
import { SupplyOrderExtendedService } from 'app/entities/supply-order-extended';
import { SupplyOrderDetailsExtendedService } from 'app/entities/supply-order-details-extended';
import { ISupplyOrderDetails, SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { ScmAreaWiseSelectedProduct } from 'app/shared/model/scm-area-wise-selected-product.model';
import { DATE_TIME_FORMAT, ScmOrderDetailsFilterPipe } from 'app/shared';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IProduct } from 'app/shared/model/product.model';
import * as moment from 'moment';
import {
    ISupplyAreaWiseAccumulation,
    SupplyAreaWiseAccumulation,
    SupplyAreaWiseAccumulationStatus
} from 'app/shared/model/supply-area-wise-accumulation.model';
import { Observable } from 'rxjs';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-update-extended',
    templateUrl: './supply-area-wise-accumulation-update-extended.component.html'
})
export class SupplyAreaWiseAccumulationUpdateExtendedComponent extends SupplyAreaWiseAccumulationUpdateComponent implements OnInit {
    supplyOrders: SupplyOrder[];
    supplyOrderDetails: SupplyOrderDetails[];
    accumulatedList: ScmAreaWiseSelectedProduct[];
    selectedProducts: SupplyOrderDetails[];
    selectedSupplyOrders: SupplyOrder[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaWiseAccumulationService: SupplyAreaWiseAccumulationExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected supplyOrderService: SupplyOrderExtendedService,
        protected supplyOrderDetailsService: SupplyOrderDetailsExtendedService,
        protected scmOrderDetailsFilterPipe: ScmOrderDetailsFilterPipe
    ) {
        super(
            jhiAlertService,
            supplyAreaWiseAccumulationService,
            supplyZoneService,
            supplyZoneManagerService,
            supplyAreaService,
            supplyAreaManagerService,
            productCategoryService,
            productService,
            activatedRoute
        );
    }

    filterZoneManager() {
        this.supplyZoneManagerService
            .query({
                'supplyZoneId.equals': this.supplyAreaWiseAccumulation.supplyZoneId,
                'status.equals': SupplyZoneManagerStatus.ACTIVE
            })
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => {
                    this.supplyzonemanagers = res;
                    this.filterArea();
                    this.filterSupplyOrders();
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    filterArea() {
        this.supplyAreaService
            .query({
                'supplyZoneId.equals': this.supplyAreaWiseAccumulation.supplyZoneId
            })
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyArea[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyArea[]) => {
                    this.supplyareas = res;
                    this.filterSupplyOrders();
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    filterAreaManager() {
        if (this.supplyAreaWiseAccumulation.supplyZoneId && this.supplyAreaWiseAccumulation.supplyAreaId) {
            this.supplyAreaManagerService
                .query({
                    'supplyZoneId.equals': this.supplyAreaWiseAccumulation.supplyZoneId,
                    'supplyAreaId.equals': this.supplyAreaWiseAccumulation.supplyAreaId,
                    'status.equals': SupplyAreaManagerStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyAreaManager[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyAreaManager[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyAreaManager[]) => {
                        this.supplyareamanagers = res;
                        this.filterSupplyOrders();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterSupplyOrders() {
        if (
            this.supplyAreaWiseAccumulation.supplyZoneId &&
            this.supplyAreaWiseAccumulation.supplyZoneManagerId &&
            this.supplyAreaWiseAccumulation.supplyAreaId &&
            this.supplyAreaWiseAccumulation.supplyAreaManagerId
        ) {
            this.supplyOrderService
                .query({
                    'supplyZoneId.equals': this.supplyAreaWiseAccumulation.supplyZoneId,
                    'supplyZoneManagerId.equals': this.supplyAreaWiseAccumulation.supplyZoneManagerId,
                    'supplyAreaId.equals': this.supplyAreaWiseAccumulation.supplyAreaId,
                    'supplyAreaManagerId.equals': this.supplyAreaWiseAccumulation.supplyAreaManagerId,
                    'status.equals': SupplyOrderStatus.ORDER_RECEIVED
                })
                .subscribe(
                    (res: HttpResponse<ISupplyOrder[]>) => {
                        this.paginateSupplyOrders(res.body, res.headers);
                        this.selectedProducts = [];
                        this.accumulatedList = [];
                        if (this.supplyOrders.length > 0) {
                            const map: string = this.supplyOrders.map(val => val.id).join(',');
                            this.supplyOrderDetailsService
                                .query({
                                    'supplyOrderId.in': [map]
                                })
                                .subscribe(
                                    (res: HttpResponse<ISupplyOrderDetails[]>) => {
                                        this.paginateSupplyOrderDetails(res.body, res.headers);
                                    },
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    ngOnInit() {
        this.accumulatedList = [];
        this.selectedProducts = [];
        this.selectedSupplyOrders = [];
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyAreaWiseAccumulation }) => {
            this.supplyAreaWiseAccumulation = supplyAreaWiseAccumulation;
            this.createdOn =
                this.supplyAreaWiseAccumulation.createdOn != null
                    ? this.supplyAreaWiseAccumulation.createdOn.format(DATE_TIME_FORMAT)
                    : null;
            this.updatedOn =
                this.supplyAreaWiseAccumulation.updatedOn != null
                    ? this.supplyAreaWiseAccumulation.updatedOn.format(DATE_TIME_FORMAT)
                    : null;
            if (!this.supplyAreaWiseAccumulation.id) {
                this.supplyAreaWiseAccumulationService
                    .query()
                    .subscribe(
                        (res: HttpResponse<ISupplyAreaWiseAccumulation[]>) =>
                            this.assignAreaWiseAccumulationReferenceNo(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        });
        this.supplyZoneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZone[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZone[]>) => response.body)
            )
            .subscribe((res: ISupplyZone[]) => (this.supplyzones = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyZoneManagerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => (this.supplyzonemanagers = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.supplyAreaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyArea[]>) => response.body)
            )
            .subscribe((res: ISupplyArea[]) => (this.supplyareas = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyAreaManagerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyAreaManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyAreaManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyAreaManager[]) => (this.supplyareamanagers = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
    }

    assignAreaWiseAccumulationReferenceNo(data: ISupplyAreaWiseAccumulation[], headers: HttpHeaders) {
        let today = new Date();
        let year = today
            .getFullYear()
            .toString()
            .substr(-2);
        let month = today.getMonth() + 1;
        let day = today.getDate();
        let hour = today.getHours();
        let minute = today.getMinutes();
        let sec = today.getSeconds();
        let maxId = data.length + 1;
        this.supplyAreaWiseAccumulation.areaWiseAccumulationRefNo =
            'AAR-' +
            year +
            ('0' + month).slice(-2) +
            ('0' + day).slice(-2) +
            ('0' + hour).slice(-2) +
            ('0' + minute).slice(-2) +
            ('0' + sec).slice(-2) +
            '-' +
            this.zeroPadding(maxId, 5);
    }

    zeroPadding(num, places): string {
        const zero = places - num.toString().length + 1;
        return Array(+(zero > 0 && zero)).join('0') + num;
    }

    selectItem(supplyOrder: SupplyOrder) {
        let selectedItems: SupplyOrderDetails[];
        selectedItems = this.scmOrderDetailsFilterPipe.transform(this.supplyOrderDetails, supplyOrder.id);

        let supplyOrderFlag: boolean = false;
        for (let a = 0; a < this.selectedSupplyOrders.length; a++) {
            if (supplyOrder.id === this.selectedSupplyOrders[a].id) {
                this.selectedSupplyOrders.splice(a, 1);
                supplyOrderFlag = true;
                break;
            }
        }
        if (!supplyOrderFlag) {
            supplyOrder.areaWiseAccumulationRefNo = this.supplyAreaWiseAccumulation.areaWiseAccumulationRefNo;
            supplyOrder.status = SupplyOrderStatus.PROCESSING_ORDER;
            this.selectedSupplyOrders.push(supplyOrder);
        }

        for (let i = 0; i < selectedItems.length; i++) {
            let found: boolean = false;
            let index: number = 0;
            for (let j = 0; j < this.selectedProducts.length; j++) {
                if (selectedItems[i].id === this.selectedProducts[j].id) {
                    found = true;
                    index = j;
                }
            }

            if (found) {
                for (let k = 0; k < this.accumulatedList.length; k++) {
                    if (
                        selectedItems[i].productCategoryId === this.accumulatedList[k].productCategoryId &&
                        selectedItems[i].productId === this.accumulatedList[k].productId
                    ) {
                        this.accumulatedList[k].quantity = this.accumulatedList[k].quantity - selectedItems[i].quantity;
                        this.accumulatedList[k].price = this.accumulatedList[k].price - selectedItems[i].price;
                    }
                }
                this.selectedProducts.splice(index, 1);
            } else {
                this.selectedProducts.push(selectedItems[i]);
                let flag: boolean = false;
                for (let k = 0; k < this.accumulatedList.length; k++) {
                    if (
                        selectedItems[i].productCategoryId === this.accumulatedList[k].productCategoryId &&
                        selectedItems[i].productId === this.accumulatedList[k].productId
                    ) {
                        this.accumulatedList[k].quantity = this.accumulatedList[k].quantity + selectedItems[i].quantity;
                        this.accumulatedList[k].price = this.accumulatedList[k].price + selectedItems[i].price;
                        flag = true;
                    }
                }

                if (!flag) {
                    let accumulated: ScmAreaWiseSelectedProduct = new ScmAreaWiseSelectedProduct(null, '', null, '', null, null);
                    accumulated.productCategoryId = selectedItems[i].productCategoryId;
                    accumulated.productId = selectedItems[i].productId;
                    accumulated.productCategoryName = selectedItems[i].productCategoryName;
                    accumulated.productCategoryName = selectedItems[i].productName;
                    accumulated.quantity = selectedItems[i].quantity;
                    accumulated.price = selectedItems[i].price;
                    this.accumulatedList.push(accumulated);
                }
            }
        }
    }

    protected paginateSupplyOrders(data: ISupplyOrder[], headers: HttpHeaders) {
        this.supplyOrders = [];
        for (let i = 0; i < data.length; i++) {
            this.supplyOrders.push(data[i]);
        }
    }

    protected paginateSupplyOrderDetails(data: ISupplyOrder[], headers: HttpHeaders) {
        this.supplyOrderDetails = [];
        for (let i = 0; i < data.length; i++) {
            this.supplyOrderDetails.push(data[i]);
        }
    }

    save() {
        this.isSaving = true;
        if (!this.supplyAreaWiseAccumulation.id) {
            if (this.selectedSupplyOrders.length > 0) {
                this.supplyOrderService.bulkUpdate(this.selectedSupplyOrders).subscribe(
                    (res: HttpResponse<ISupplyOrder[]>) => {
                        let supplyAreaWiseAccumulations: SupplyAreaWiseAccumulation[] = [];
                        for (let i = 0; i < this.accumulatedList.length; i++) {
                            let areaWiseAccumulation: SupplyAreaWiseAccumulation = {};
                            areaWiseAccumulation.supplyZoneId = this.supplyAreaWiseAccumulation.supplyZoneId;
                            areaWiseAccumulation.supplyAreaId = this.supplyAreaWiseAccumulation.supplyAreaId;
                            areaWiseAccumulation.supplyZoneManagerId = this.supplyAreaWiseAccumulation.supplyZoneManagerId;
                            areaWiseAccumulation.supplyAreaManagerId = this.supplyAreaWiseAccumulation.supplyAreaManagerId;
                            areaWiseAccumulation.productCategoryId = this.accumulatedList[i].productCategoryId;
                            areaWiseAccumulation.productId = this.accumulatedList[i].productId;
                            areaWiseAccumulation.quantity = this.accumulatedList[i].quantity;
                            areaWiseAccumulation.price = this.accumulatedList[i].price;
                            areaWiseAccumulation.areaWiseAccumulationRefNo = this.supplyAreaWiseAccumulation.areaWiseAccumulationRefNo;
                            areaWiseAccumulation.price = this.accumulatedList[i].price;
                            areaWiseAccumulation.status = SupplyAreaWiseAccumulationStatus.FORWARDED;
                            areaWiseAccumulation.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
                            areaWiseAccumulation.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;

                            supplyAreaWiseAccumulations.push(areaWiseAccumulation);
                        }
                        this.subscribeToBulkSaveResponse(this.supplyAreaWiseAccumulationService.bulkPost(supplyAreaWiseAccumulations));
                    },
                    (res: HttpErrorResponse) => this.onSaveError()
                );
            } else {
                this.jhiAlertService.error('Nothing selected!!');
            }
        } else {
            this.subscribeToSaveResponse(this.supplyAreaWiseAccumulationService.update(this.supplyAreaWiseAccumulation));
        }
    }

    protected subscribeToBulkSaveResponse(result: Observable<HttpResponse<ISupplyAreaWiseAccumulation[]>>) {
        result.subscribe(
            (res: HttpResponse<ISupplyAreaWiseAccumulation[]>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    trackId(index: number, item: ISupplyOrder) {
        return item.id;
    }
}
