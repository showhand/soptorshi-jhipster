import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService } from 'app/entities/supply-area';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative';
import { SupplyOrderService, SupplyOrderUpdateComponent } from 'app/entities/supply-order';

@Component({
    selector: 'jhi-supply-order-update-extended',
    templateUrl: './supply-order-update-extended.component.html'
})
export class SupplyOrderUpdateExtendedComponent extends SupplyOrderUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyOrderService: SupplyOrderService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyAreaService: SupplyAreaService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyOrderService,
            supplyZoneService,
            supplyAreaService,
            supplySalesRepresentativeService,
            supplyAreaManagerService,
            activatedRoute
        );
    }
}
