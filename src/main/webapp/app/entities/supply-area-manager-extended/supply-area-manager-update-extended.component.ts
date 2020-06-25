import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyAreaManagerExtendedService } from './supply-area-manager-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService } from 'app/entities/supply-area';
import { EmployeeService } from 'app/entities/employee';
import { SupplyAreaManagerUpdateComponent } from 'app/entities/supply-area-manager';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';

@Component({
    selector: 'jhi-supply-area-manager-update-extended',
    templateUrl: './supply-area-manager-update-extended.component.html'
})
export class SupplyAreaManagerUpdateExtendedComponent extends SupplyAreaManagerUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyAreaService: SupplyAreaService,
        protected employeeService: EmployeeService,
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

    filterSupplyAreaAndSupplyZoneManager() {
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
