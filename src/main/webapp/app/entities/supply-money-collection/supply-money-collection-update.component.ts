import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';
import { SupplyMoneyCollectionService } from './supply-money-collection.service';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { SupplyAreaService } from 'app/entities/supply-area';
import { ISupplyAreaManager, SupplyAreaManagerStatus } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { ISupplySalesRepresentative, SupplySalesRepresentativeStatus } from 'app/shared/model/supply-sales-representative.model';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';
import { SupplyShopService } from 'app/entities/supply-shop';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from 'app/entities/supply-order';
import { SupplyOrderDetailsExtendedService } from 'app/entities/supply-order-details-extended';
import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

@Component({
    selector: 'jhi-supply-money-collection-update',
    templateUrl: './supply-money-collection-update.component.html'
})
export class SupplyMoneyCollectionUpdateComponent implements OnInit {
    supplyMoneyCollection: ISupplyMoneyCollection;
    isSaving: boolean;

    supplyzones: ISupplyZone[];

    supplyzonemanagers: ISupplyZoneManager[];

    supplyareas: ISupplyArea[];

    supplyareamanagers: ISupplyAreaManager[];

    supplysalesrepresentatives: ISupplySalesRepresentative[];

    supplyshops: ISupplyShop[];

    supplyorders: ISupplyOrder[];
    supplyordersdetails: ISupplyOrderDetails[];
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyMoneyCollectionService: SupplyMoneyCollectionService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        protected supplyShopService: SupplyShopService,
        protected supplyOrderService: SupplyOrderService,
        protected activatedRoute: ActivatedRoute,
        protected supplyOrderDetailsExtendedService: SupplyOrderDetailsExtendedService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyMoneyCollection }) => {
            this.supplyMoneyCollection = supplyMoneyCollection;
            this.createdOn =
                this.supplyMoneyCollection.createdOn != null ? this.supplyMoneyCollection.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn =
                this.supplyMoneyCollection.updatedOn != null ? this.supplyMoneyCollection.updatedOn.format(DATE_TIME_FORMAT) : null;
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
        this.supplyShopService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyShop[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyShop[]>) => response.body)
            )
            .subscribe((res: ISupplyShop[]) => (this.supplyshops = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyOrder[]>) => response.body)
            )
            .subscribe((res: ISupplyOrder[]) => (this.supplyorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplyMoneyCollection.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyMoneyCollection.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyMoneyCollection.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyMoneyCollectionService.update(this.supplyMoneyCollection));
        } else {
            this.subscribeToSaveResponse(this.supplyMoneyCollectionService.create(this.supplyMoneyCollection));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyMoneyCollection>>) {
        result.subscribe(
            (res: HttpResponse<ISupplyMoneyCollection>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackSupplyZoneById(index: number, item: ISupplyZone) {
        return item.id;
    }

    trackSupplyZoneManagerById(index: number, item: ISupplyZoneManager) {
        return item.id;
    }

    trackSupplyAreaById(index: number, item: ISupplyArea) {
        return item.id;
    }

    trackSupplyAreaManagerById(index: number, item: ISupplyAreaManager) {
        return item.id;
    }

    trackSupplySalesRepresentativeById(index: number, item: ISupplySalesRepresentative) {
        return item.id;
    }

    trackSupplyShopById(index: number, item: ISupplyShop) {
        return item.id;
    }

    trackSupplyOrderById(index: number, item: ISupplyOrder) {
        return item.id;
    }

    filterZoneManager() {
        if (this.supplyMoneyCollection.supplyZoneId) {
            this.supplyZoneManagerService
                .query({
                    'supplyZoneId.equals': this.supplyMoneyCollection.supplyZoneId,
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
        if (this.supplyMoneyCollection.supplyZoneId) {
            this.supplyAreaService
                .query({
                    'supplyZoneId.equals': this.supplyMoneyCollection.supplyZoneId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyArea[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyArea[]) => {
                        this.supplyareas = res;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterAreaManager() {
        if (
            this.supplyMoneyCollection.supplyZoneId &&
            this.supplyMoneyCollection.supplyAreaId &&
            this.supplyMoneyCollection.supplyZoneManagerId
        ) {
            this.supplyAreaManagerService
                .query({
                    'supplyZoneId.equals': this.supplyMoneyCollection.supplyZoneId,
                    'supplyAreaId.equals': this.supplyMoneyCollection.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyMoneyCollection.supplyZoneManagerId,
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
            this.supplyMoneyCollection.supplyZoneId &&
            this.supplyMoneyCollection.supplyAreaId &&
            this.supplyMoneyCollection.supplyZoneManagerId &&
            this.supplyMoneyCollection.supplyAreaManagerId
        ) {
            this.supplySalesRepresentativeService
                .query({
                    'supplyZoneId.equals': this.supplyMoneyCollection.supplyZoneId,
                    'supplyAreaId.equals': this.supplyMoneyCollection.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyMoneyCollection.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyMoneyCollection.supplyAreaManagerId,
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
            this.supplyMoneyCollection.supplyZoneId &&
            this.supplyMoneyCollection.supplyAreaId &&
            this.supplyMoneyCollection.supplyZoneManagerId &&
            this.supplyMoneyCollection.supplyAreaManagerId &&
            this.supplyMoneyCollection.supplySalesRepresentativeId
        ) {
            this.supplyShopService
                .query({
                    'supplyZoneId.equals': this.supplyMoneyCollection.supplyZoneId,
                    'supplyAreaId.equals': this.supplyMoneyCollection.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyMoneyCollection.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyMoneyCollection.supplyAreaManagerId,
                    'supplySalesRepresentativeId.equals': this.supplyMoneyCollection.supplySalesRepresentativeId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyShop[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyShop[]>) => response.body)
                )
                .subscribe((res: ISupplyShop[]) => (this.supplyshops = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    filterOrder() {
        if (
            this.supplyMoneyCollection.supplyZoneId &&
            this.supplyMoneyCollection.supplyAreaId &&
            this.supplyMoneyCollection.supplyZoneManagerId &&
            this.supplyMoneyCollection.supplyAreaManagerId &&
            this.supplyMoneyCollection.supplySalesRepresentativeId &&
            this.supplyMoneyCollection.supplyShopId
        ) {
            this.supplyOrderService
                .query({
                    'supplyZoneId.equals': this.supplyMoneyCollection.supplyZoneId,
                    'supplyAreaId.equals': this.supplyMoneyCollection.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyMoneyCollection.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyMoneyCollection.supplyAreaManagerId,
                    'supplySalesRepresentativeId.equals': this.supplyMoneyCollection.supplySalesRepresentativeId,
                    'supplyShopId.equals': this.supplyMoneyCollection.supplyShopId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyOrder[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyOrder[]>) => response.body)
                )
                .subscribe((res: ISupplyOrder[]) => (this.supplyorders = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    accumulatePrice() {
        if (
            this.supplyMoneyCollection.supplyZoneId &&
            this.supplyMoneyCollection.supplyAreaId &&
            this.supplyMoneyCollection.supplyZoneManagerId &&
            this.supplyMoneyCollection.supplyAreaManagerId &&
            this.supplyMoneyCollection.supplySalesRepresentativeId &&
            this.supplyMoneyCollection.supplyShopId &&
            this.supplyMoneyCollection.supplyOrderId
        ) {
            this.supplyOrderDetailsExtendedService
                .query({
                    'supplyOrderId.equals': this.supplyMoneyCollection.supplyOrderId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyOrderDetails[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyOrderDetails[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyOrderDetails[]) => {
                        this.supplyordersdetails = res;
                        this.supplyMoneyCollection.totalAmount = 0;
                        for (let i = 0; i < this.supplyordersdetails.length; i++) {
                            this.supplyMoneyCollection.totalAmount += this.supplyordersdetails[i].price;
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }
}
