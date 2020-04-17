import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyShopExtendedService } from './supply-shop-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService } from 'app/entities/supply-area';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative';
import { SupplyShopUpdateComponent } from 'app/entities/supply-shop';

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
}
