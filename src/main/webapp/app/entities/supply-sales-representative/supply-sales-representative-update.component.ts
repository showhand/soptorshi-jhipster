import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';
import { SupplySalesRepresentativeService } from './supply-sales-representative.service';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { SupplyAreaService } from 'app/entities/supply-area';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';

@Component({
    selector: 'jhi-supply-sales-representative-update',
    templateUrl: './supply-sales-representative-update.component.html'
})
export class SupplySalesRepresentativeUpdateComponent implements OnInit {
    supplySalesRepresentative: ISupplySalesRepresentative;
    isSaving: boolean;

    supplyzones: ISupplyZone[];

    supplyareas: ISupplyArea[];

    supplyzonemanagers: ISupplyZoneManager[];

    supplyareamanagers: ISupplyAreaManager[];
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplySalesRepresentative }) => {
            this.supplySalesRepresentative = supplySalesRepresentative;
            this.createdOn =
                this.supplySalesRepresentative.createdOn != null ? this.supplySalesRepresentative.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn =
                this.supplySalesRepresentative.updatedOn != null ? this.supplySalesRepresentative.updatedOn.format(DATE_TIME_FORMAT) : null;
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplySalesRepresentative.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplySalesRepresentative.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplySalesRepresentative.id !== undefined) {
            this.subscribeToSaveResponse(this.supplySalesRepresentativeService.update(this.supplySalesRepresentative));
        } else {
            this.subscribeToSaveResponse(this.supplySalesRepresentativeService.create(this.supplySalesRepresentative));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplySalesRepresentative>>) {
        result.subscribe(
            (res: HttpResponse<ISupplySalesRepresentative>) => this.onSaveSuccess(),
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

    trackSupplyAreaById(index: number, item: ISupplyArea) {
        return item.id;
    }

    trackSupplyZoneManagerById(index: number, item: ISupplyZoneManager) {
        return item.id;
    }

    trackSupplyAreaManagerById(index: number, item: ISupplyAreaManager) {
        return item.id;
    }
}
