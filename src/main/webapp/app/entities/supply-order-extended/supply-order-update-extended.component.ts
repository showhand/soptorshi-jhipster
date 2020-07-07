import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyOrderUpdateComponent } from 'app/entities/supply-order';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplySalesRepresentative, SupplySalesRepresentativeStatus } from 'app/shared/model/supply-sales-representative.model';
import { ISupplyAreaManager, SupplyAreaManagerStatus } from 'app/shared/model/supply-area-manager.model';
import { SupplyOrderDetailsExtendedService } from 'app/entities/supply-order-details-extended';
import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { SupplyOrderExtendedService } from 'app/entities/supply-order-extended/supply-order-extended.service';
import { SupplyZoneExtendedService } from 'app/entities/supply-zone-extended';
import { SupplyAreaExtendedService } from 'app/entities/supply-area-extended';
import { SupplySalesRepresentativeExtendedService } from 'app/entities/supply-sales-representative-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import * as moment from 'moment';
import { ISupplyOrder, SupplyOrderStatus } from 'app/shared/model/supply-order.model';
import { SupplyShopExtendedService } from 'app/entities/supply-shop-extended';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';

@Component({
    selector: 'jhi-supply-order-update-extended',
    templateUrl: './supply-order-update-extended.component.html'
})
export class SupplyOrderUpdateExtendedComponent extends SupplyOrderUpdateComponent implements OnInit {
    supplyOrderDetails: ISupplyOrderDetails[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyOrderService: SupplyOrderExtendedService,
        protected supplyZoneService: SupplyZoneExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected supplyAreaService: SupplyAreaExtendedService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplyShopService: SupplyShopExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected supplyOrderDetailsService: SupplyOrderDetailsExtendedService,
        protected router: Router
    ) {
        super(
            jhiAlertService,
            supplyOrderService,
            supplyZoneService,
            supplyZoneManagerService,
            supplyAreaService,
            supplySalesRepresentativeService,
            supplyAreaManagerService,
            supplyShopService,
            activatedRoute
        );
    }

    filterZoneManager() {
        if (this.supplyOrder.supplyZoneId) {
            this.supplyZoneManagerService
                .query({
                    'supplyZoneId.equals': this.supplyOrder.supplyZoneId,
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
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterArea() {
        if (this.supplyOrder.supplyZoneId) {
            this.supplyAreaService
                .query({
                    'supplyZoneId.equals': this.supplyOrder.supplyZoneId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyArea[]>) => response.body)
                )
                .subscribe((res: ISupplyArea[]) => (this.supplyareas = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    filterAreaManager() {
        if (this.supplyOrder.supplyZoneId && this.supplyOrder.supplyAreaId && this.supplyOrder.supplyZoneManagerId) {
            this.supplyAreaManagerService
                .query({
                    'supplyZoneId.equals': this.supplyOrder.supplyZoneId,
                    'supplyAreaId.equals': this.supplyOrder.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyOrder.supplyZoneManagerId,
                    'status.equals': SupplyAreaManagerStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyAreaManager[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyAreaManager[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyAreaManager[]) => {
                        this.supplyareamanagers = res;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterSalesRepresentative() {
        if (
            this.supplyOrder.supplyZoneId &&
            this.supplyOrder.supplyAreaId &&
            this.supplyOrder.supplyZoneManagerId &&
            this.supplyOrder.supplyAreaManagerId
        ) {
            this.supplySalesRepresentativeService
                .query({
                    'supplyZoneId.equals': this.supplyOrder.supplyZoneId,
                    'supplyAreaId.equals': this.supplyOrder.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyOrder.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyOrder.supplyAreaManagerId,
                    'status.equals': SupplySalesRepresentativeStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplySalesRepresentative[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplySalesRepresentative[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplySalesRepresentative[]) => (this.supplysalesrepresentatives = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterShop() {
        if (
            this.supplyOrder.supplyZoneId &&
            this.supplyOrder.supplyAreaId &&
            this.supplyOrder.supplyZoneManagerId &&
            this.supplyOrder.supplyAreaManagerId &&
            this.supplyOrder.supplySalesRepresentativeId
        ) {
            this.supplyShopService
                .query({
                    'supplyZoneId.equals': this.supplyOrder.supplyZoneId,
                    'supplyAreaId.equals': this.supplyOrder.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyOrder.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyOrder.supplyAreaManagerId,
                    'supplySalesRepresentativeId.equals': this.supplyOrder.supplySalesRepresentativeId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyShop[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyShop[]>) => response.body)
                )
                .subscribe((res: ISupplyShop[]) => (this.supplyshops = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyOrder }) => {
            this.supplyOrder = supplyOrder;
            this.createdOn = this.supplyOrder.createdOn != null ? this.supplyOrder.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyOrder.updatedOn != null ? this.supplyOrder.updatedOn.format(DATE_TIME_FORMAT) : null;

            this.getProductInfo();
            if (!this.supplyOrder.id) {
                this.supplyOrderService
                    .query()
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.assignOrderId(res.body, res.headers),
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
        this.supplySalesRepresentativeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplySalesRepresentative[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplySalesRepresentative[]>) => response.body)
            )
            .subscribe(
                (res: ISupplySalesRepresentative[]) => (this.supplysalesrepresentatives = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        this.supplyShopService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyShop[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyShop[]>) => response.body)
            )
            .subscribe((res: ISupplyShop[]) => (this.supplyshops = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getProductInfo() {
        this.supplyOrderDetailsService
            .query({
                'supplyOrderId.equals': this.supplyOrder.id == undefined ? 0 : this.supplyOrder.id
            })
            .subscribe(
                (res: HttpResponse<ISupplyOrderDetails[]>) => this.assignProductInfos(res.body, res.headers),
                (res: HttpErrorResponse) => 'error'
            );
    }

    assignOrderId(data: ISupplyOrder[], headers: HttpHeaders) {
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
        this.supplyOrder.orderNo =
            'O-' +
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

    protected assignProductInfos(data: ISupplyOrderDetails[], headers: HttpHeaders) {
        this.supplyOrderDetails = [];
        for (let i = 0; i < data.length; i++) {
            this.supplyOrderDetails.push(data[i]);
        }
    }

    previousState() {
        /*window.history.back();*/
        this.router.navigate(['/supply-order']);
    }

    save() {
        this.isSaving = true;
        this.supplyOrder.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyOrder.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyOrderService.update(this.supplyOrder));
        } else {
            this.supplyOrder.status = SupplyOrderStatus.ORDER_RECEIVED;
            this.subscribeToSaveResponse(this.supplyOrderService.create(this.supplyOrder));
        }
    }
}
