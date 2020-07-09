import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyChallanExtendedService } from './supply-challan-extended.service';
import { SupplyChallanUpdateComponent } from 'app/entities/supply-challan';
import { SupplyZoneExtendedService } from 'app/entities/supply-zone-extended';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { SupplyAreaExtendedService } from 'app/entities/supply-area-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { SupplySalesRepresentativeExtendedService } from 'app/entities/supply-sales-representative-extended';
import { SupplyShopExtendedService } from 'app/entities/supply-shop-extended';
import { SupplyOrderExtendedService } from 'app/entities/supply-order-extended';

@Component({
    selector: 'jhi-supply-challan-update-extended',
    templateUrl: './supply-challan-update-extended.component.html'
})
export class SupplyChallanUpdateExtendedComponent extends SupplyChallanUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyChallanService: SupplyChallanExtendedService,
        protected supplyZoneService: SupplyZoneExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected supplyAreaService: SupplyAreaExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        protected supplyShopService: SupplyShopExtendedService,
        protected supplyOrderService: SupplyOrderExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyChallanService,
            supplyZoneService,
            supplyZoneManagerService,
            supplyAreaService,
            supplyAreaManagerService,
            supplySalesRepresentativeService,
            supplyShopService,
            supplyOrderService,
            activatedRoute
        );
    }
}
