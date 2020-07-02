import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from './supply-order.service';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { SupplyAreaService } from 'app/entities/supply-area';
import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';
import { SupplyShopService } from 'app/entities/supply-shop';

@Component({
    selector: 'jhi-supply-order-update',
    templateUrl: './supply-order-update.component.html'
})
export class SupplyOrderUpdateComponent implements OnInit {
    supplyOrder: ISupplyOrder;
    isSaving: boolean;

    supplyzones: ISupplyZone[];

    supplyzonemanagers: ISupplyZoneManager[];

    supplyareas: ISupplyArea[];

    supplysalesrepresentatives: ISupplySalesRepresentative[];

    supplyareamanagers: ISupplyAreaManager[];

    supplyshops: ISupplyShop[];
    dateOfOrderDp: any;
    createdOn: string;
    updatedOn: string;
    deliveryDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyOrderService: SupplyOrderService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyAreaService: SupplyAreaService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected supplyShopService: SupplyShopService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyOrder }) => {
            this.supplyOrder = supplyOrder;
            this.createdOn = this.supplyOrder.createdOn != null ? this.supplyOrder.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyOrder.updatedOn != null ? this.supplyOrder.updatedOn.format(DATE_TIME_FORMAT) : null;
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplyOrder.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyOrder.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyOrderService.update(this.supplyOrder));
        } else {
            this.subscribeToSaveResponse(this.supplyOrderService.create(this.supplyOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyOrder>>) {
        result.subscribe((res: HttpResponse<ISupplyOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSupplySalesRepresentativeById(index: number, item: ISupplySalesRepresentative) {
        return item.id;
    }

    trackSupplyAreaManagerById(index: number, item: ISupplyAreaManager) {
        return item.id;
    }

    trackSupplyShopById(index: number, item: ISupplyShop) {
        return item.id;
    }
}
