import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyAreaManagerExtendedService } from './supply-area-manager-extended.service';
import { SupplyAreaManagerUpdateComponent } from 'app/entities/supply-area-manager';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import { DATE_TIME_FORMAT } from 'app/shared';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { SupplyZoneExtendedService } from 'app/entities/supply-zone-extended';
import { SupplyAreaExtendedService } from 'app/entities/supply-area-extended';
import { EmployeeExtendedService } from 'app/entities/employee-extended';

@Component({
    selector: 'jhi-supply-area-manager-update-extended',
    templateUrl: './supply-area-manager-update-extended.component.html'
})
export class SupplyAreaManagerUpdateExtendedComponent extends SupplyAreaManagerUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplyZoneService: SupplyZoneExtendedService,
        protected supplyAreaService: SupplyAreaExtendedService,
        protected employeeService: EmployeeExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyAreaManagerService,
            supplyZoneService,
            supplyAreaService,
            employeeService,
            supplyZoneManagerService,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyAreaManager }) => {
            this.supplyAreaManager = supplyAreaManager;
            this.createdOn = this.supplyAreaManager.createdOn != null ? this.supplyAreaManager.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyAreaManager.updatedOn != null ? this.supplyAreaManager.updatedOn.format(DATE_TIME_FORMAT) : null;
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
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    }

    filterSupplyAreaAndSupplyZoneManager() {
        if (this.supplyAreaManager.supplyZoneId) {
            this.supplyAreaService
                .query({
                    'supplyZoneId.equals': this.supplyAreaManager.supplyZoneId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyArea[]>) => response.body)
                )
                .subscribe((res: ISupplyArea[]) => (this.supplyareas = res), (res: HttpErrorResponse) => this.onError(res.message));

            this.supplyZoneManagerService
                .query({
                    'supplyZoneId.equals': this.supplyAreaManager.supplyZoneId,
                    'status.equals': SupplyZoneManagerStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyZoneManager[]) => (this.supplyzonemanagers = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }
}
