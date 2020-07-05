import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService, SupplyAreaUpdateComponent } from 'app/entities/supply-area';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';

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
}
