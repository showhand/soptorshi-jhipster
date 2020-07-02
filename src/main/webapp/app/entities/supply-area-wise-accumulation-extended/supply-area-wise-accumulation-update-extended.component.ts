import { Component } from '@angular/core';
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

@Component({
    selector: 'jhi-supply-area-wise-accumulation-update-extended',
    templateUrl: './supply-area-wise-accumulation-update-extended.component.html'
})
export class SupplyAreaWiseAccumulationUpdateExtendedComponent extends SupplyAreaWiseAccumulationUpdateComponent {
    supplyOrders: SupplyOrder[];
    supplyOrderDetails: SupplyOrderDetails[];

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
        protected supplyOrder: SupplyOrderExtendedService,
        protected supplyOrderDetailsService: SupplyOrderDetailsExtendedService
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
            this.supplyOrder
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
                        const map: string = this.supplyOrders.map(val => val.id).join(',');
                        this.supplyOrderDetailsService
                            .query({
                                'supplyOrderId.in': [map]
                            })
                            .subscribe(
                                (res: HttpResponse<ISupplyOrderDetails[]>) => {
                                    this.paginateSupplyOrderDetails(res.body, res.headers);
                                    this.mapSupplyOrderAndSupplyOrderDetails();
                                },
                                (res: HttpErrorResponse) => this.onError(res.message)
                            );
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
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

    protected mapSupplyOrderAndSupplyOrderDetails() {
        for (let a = 0; a < this.supplyOrders.length; a++) {
            let orderDetails: SupplyOrderDetails[] = [];

            for (let b = 0; b < this.supplyOrderDetails.length; b++) {
                if (this.supplyOrders[a].id === this.supplyOrderDetails[b].supplyOrderId) {
                    orderDetails.push(this.supplyOrderDetails[b]);
                }
            }

            //this.supplyOrders[a].supplyOrderDetails = orderDetails;
        }
    }

    selectOrder(supplyOrder: SupplyOrder) {
        /*for(let i = 0; i < supplyOrder.supplyOrderDetails.length; i++) {
            this.supplyAreaWiseAccumulation.quantity = 0;
            this.supplyAreaWiseAccumulation.price = 0;
            for (let j = 0; i < supplyOrder.supplyOrderDetails.length; j++) {
                if (supplyOrder.supplyOrderDetails[i].productCategoryId === supplyOrder.supplyOrderDetails[j].productCategoryId &&
                    supplyOrder.supplyOrderDetails[i].productId === supplyOrder.supplyOrderDetails[j].productId) {
                    this.supplyAreaWiseAccumulation.quantity = supplyOrder.supplyOrderDetails[i].quantity;
                    this.supplyAreaWiseAccumulation.price = supplyOrder.supplyOrderDetails[i].price;
                }
            }
        }*/
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.filterSupplyOrders();
        //this.previousState();
    }

    trackId(index: number, item: ISupplyOrder) {
        return item.id;
    }
}
