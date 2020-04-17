import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyAreaExtendedService } from './supply-area-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaUpdateComponent } from 'app/entities/supply-area';

@Component({
    selector: 'jhi-supply-area-update-extended',
    templateUrl: './supply-area-update-extended.component.html'
})
export class SupplyAreaUpdateExtendedComponent extends SupplyAreaUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaService: SupplyAreaExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, supplyAreaService, supplyZoneService, activatedRoute);
    }
}
