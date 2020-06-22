import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplySalesRepresentativeExtendedService } from './supply-sales-representative-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService } from 'app/entities/supply-area';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { SupplySalesRepresentativeUpdateComponent } from 'app/entities/supply-sales-representative';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';

@Component({
    selector: 'jhi-supply-sales-representative-update-extended',
    templateUrl: './supply-sales-representative-update-extended.component.html'
})
export class SupplySalesRepresentativeUpdateExtendedComponent extends SupplySalesRepresentativeUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplySalesRepresentativeService,
            supplyZoneService,
            supplyAreaService,
            supplyAreaManagerService,
            activatedRoute
        );
    }

    filterArea() {
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

    filterAreaManager() {
        this.supplyAreaManagerService
            .query({
                'supplyZoneId.equals': this.supplySalesRepresentative.supplyZoneId,
                'supplyAreaId.equals': this.supplySalesRepresentative.supplyAreaId
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
