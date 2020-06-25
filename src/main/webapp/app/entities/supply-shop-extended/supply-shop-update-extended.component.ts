import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyShopExtendedService } from './supply-shop-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService } from 'app/entities/supply-area';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative';
import { SupplyShopUpdateComponent } from 'app/entities/supply-shop';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplyAreaManager, SupplyAreaManagerStatus } from 'app/shared/model/supply-area-manager.model';
import { ISupplySalesRepresentative, SupplySalesRepresentativeStatus } from 'app/shared/model/supply-sales-representative.model';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';

@Component({
    selector: 'jhi-supply-shop-update-extended',
    templateUrl: './supply-shop-update-extended.component.html'
})
export class SupplyShopUpdateExtendedComponent extends SupplyShopUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyShopService: SupplyShopExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyShopService,
            supplyZoneService,
            supplyAreaService,
            supplyZoneManagerService,
            supplyAreaManagerService,
            supplySalesRepresentativeService,
            activatedRoute
        );
    }

    filterZoneManager() {
        this.supplyZoneManagerService
            .query({
                'supplyZoneId.equals': this.supplyShop.supplyZoneId,
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

    filterArea() {
        this.supplyAreaService
            .query({
                'supplyZoneId.equals': this.supplyShop.supplyZoneId
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
                'supplyZoneId.equals': this.supplyShop.supplyZoneId,
                'supplyAreaId.equals': this.supplyShop.supplyAreaId,
                'supplyZoneManagerId.equals': this.supplyShop.supplyZoneManagerId,
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

    filterSalesRepresentative() {
        this.supplySalesRepresentativeService
            .query({
                'supplyZoneId.equals': this.supplyShop.supplyZoneId,
                'supplyAreaId.equals': this.supplyShop.supplyAreaId,
                'supplyAreaManagerId.equals': this.supplyShop.supplyAreaManagerId,
                'supplyZoneManagerId.equals': this.supplyShop.supplyZoneManagerId,
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
