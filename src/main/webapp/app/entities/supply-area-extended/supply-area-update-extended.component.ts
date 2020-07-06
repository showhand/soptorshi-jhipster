import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyAreaUpdateComponent } from 'app/entities/supply-area';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import { SupplyAreaExtendedService } from 'app/entities/supply-area-extended/supply-area-extended.service';
import { SupplyZoneExtendedService } from 'app/entities/supply-zone-extended';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';

@Component({
    selector: 'jhi-supply-area-update-extended',
    templateUrl: './supply-area-update-extended.component.html'
})
export class SupplyAreaUpdateExtendedComponent extends SupplyAreaUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaService: SupplyAreaExtendedService,
        protected supplyZoneService: SupplyZoneExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, supplyAreaService, supplyZoneService, supplyZoneManagerService, activatedRoute);
    }

    filterSupplyZoneManager() {
        if (this.supplyArea.supplyZoneId) {
            this.supplyZoneManagerService
                .query({
                    'supplyZoneId.equals': this.supplyArea.supplyZoneId,
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
