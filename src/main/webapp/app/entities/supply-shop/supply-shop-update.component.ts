import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';
import { SupplyShopService } from './supply-shop.service';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { SupplyAreaService } from 'app/entities/supply-area';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative';

@Component({
    selector: 'jhi-supply-shop-update',
    templateUrl: './supply-shop-update.component.html'
})
export class SupplyShopUpdateComponent implements OnInit {
    supplyShop: ISupplyShop;
    isSaving: boolean;

    supplyzones: ISupplyZone[];

    supplyareas: ISupplyArea[];

    supplyzonemanagers: ISupplyZoneManager[];

    supplyareamanagers: ISupplyAreaManager[];

    supplysalesrepresentatives: ISupplySalesRepresentative[];
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyShopService: SupplyShopService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyShop }) => {
            this.supplyShop = supplyShop;
            this.createdOn = this.supplyShop.createdOn != null ? this.supplyShop.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyShop.updatedOn != null ? this.supplyShop.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.supplyZoneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZone[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZone[]>) => response.body)
            )
            .subscribe((res: ISupplyZone[]) => (this.supplyzones = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyAreaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyArea[]>) => response.body)
            )
            .subscribe((res: ISupplyArea[]) => (this.supplyareas = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplyShop.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyShop.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyShop.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyShopService.update(this.supplyShop));
        } else {
            this.subscribeToSaveResponse(this.supplyShopService.create(this.supplyShop));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyShop>>) {
        result.subscribe((res: HttpResponse<ISupplyShop>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSupplyAreaById(index: number, item: ISupplyArea) {
        return item.id;
    }

    trackSupplyZoneManagerById(index: number, item: ISupplyZoneManager) {
        return item.id;
    }

    trackSupplyAreaManagerById(index: number, item: ISupplyAreaManager) {
        return item.id;
    }

    trackSupplySalesRepresentativeById(index: number, item: ISupplySalesRepresentative) {
        return item.id;
    }
}
