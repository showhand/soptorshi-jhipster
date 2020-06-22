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
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';

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
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyShopService,
            supplyZoneService,
            supplyAreaService,
            supplySalesRepresentativeService,
            supplyAreaManagerService,
            activatedRoute
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
                'supplyAreaId.equals': this.supplyShop.supplyAreaId
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
                'supplyAreaManagerId.equals': this.supplyShop.supplyAreaManagerId
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
