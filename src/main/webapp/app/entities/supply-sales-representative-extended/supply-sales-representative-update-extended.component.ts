import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplySalesRepresentativeExtendedService } from './supply-sales-representative-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService } from 'app/entities/supply-area';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { SupplySalesRepresentativeUpdateComponent } from 'app/entities/supply-sales-representative';

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
}
