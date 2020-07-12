import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplySalesRepresentativeExtendedService } from './supply-sales-representative-extended.service';
import { SupplySalesRepresentativeUpdateComponent } from 'app/entities/supply-sales-representative';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplyAreaManager, SupplyAreaManagerStatus } from 'app/shared/model/supply-area-manager.model';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import { DATE_TIME_FORMAT } from 'app/shared';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneExtendedService } from 'app/entities/supply-zone-extended';
import { SupplyAreaExtendedService } from 'app/entities/supply-area-extended';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';

@Component({
    selector: 'jhi-supply-sales-representative-update-extended',
    templateUrl: './supply-sales-representative-update-extended.component.html'
})
export class SupplySalesRepresentativeUpdateExtendedComponent extends SupplySalesRepresentativeUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        protected supplyZoneService: SupplyZoneExtendedService,
        protected supplyAreaService: SupplyAreaExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplySalesRepresentativeService,
            supplyZoneService,
            supplyAreaService,
            supplyZoneManagerService,
            supplyAreaManagerService,
            activatedRoute
        );
    }

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

    filterZoneManager() {
        if (this.supplySalesRepresentative.supplyZoneId) {
            this.supplyZoneManagerService
                .query({
                    'supplyZoneId.equals': this.supplySalesRepresentative.supplyZoneId,
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
        if (this.supplySalesRepresentative.supplyZoneId) {
            this.supplyAreaService
                .query({
                    'supplyZoneId.equals': this.supplySalesRepresentative.supplyZoneId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyArea[]>) => response.body)
                )
                .subscribe((res: ISupplyArea[]) => (this.supplyareas = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    filterAreaManager() {
        if (
            this.supplySalesRepresentative.supplyZoneId &&
            this.supplySalesRepresentative.supplyAreaId &&
            this.supplySalesRepresentative.supplyZoneManagerId
        ) {
            this.supplyAreaManagerService
                .query({
                    'supplyZoneId.equals': this.supplySalesRepresentative.supplyZoneId,
                    'supplyAreaId.equals': this.supplySalesRepresentative.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplySalesRepresentative.supplyZoneManagerId,
                    'status.equals': SupplyAreaManagerStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyAreaManager[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyAreaManager[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyAreaManager[]) => (this.supplyareamanagers = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }
}
