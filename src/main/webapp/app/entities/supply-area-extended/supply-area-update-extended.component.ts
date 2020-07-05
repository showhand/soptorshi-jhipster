import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService, SupplyAreaUpdateComponent } from 'app/entities/supply-area';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';

@Component({
    selector: 'jhi-supply-area-update-extended',
    templateUrl: './supply-area-update-extended.component.html'
})
export class SupplyAreaUpdateExtendedComponent extends SupplyAreaUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
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
