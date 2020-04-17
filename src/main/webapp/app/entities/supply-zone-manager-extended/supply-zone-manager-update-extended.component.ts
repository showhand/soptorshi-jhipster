import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyZoneManagerExtendedService } from './supply-zone-manager-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { EmployeeService } from 'app/entities/employee';
import { SupplyZoneManagerUpdateComponent } from 'app/entities/supply-zone-manager';

@Component({
    selector: 'jhi-supply-zone-manager-update-extended',
    templateUrl: './supply-zone-manager-update-extended.component.html'
})
export class SupplyZoneManagerUpdateExtendedComponent extends SupplyZoneManagerUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, supplyZoneManagerService, supplyZoneService, employeeService, activatedRoute);
    }
}
