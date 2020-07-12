import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyZoneManagerExtendedService } from './supply-zone-manager-extended.service';
import { SupplyZoneManagerUpdateComponent } from 'app/entities/supply-zone-manager';
import { SupplyZoneExtendedService } from 'app/entities/supply-zone-extended';
import { EmployeeExtendedService } from 'app/entities/employee-extended';

@Component({
    selector: 'jhi-supply-zone-manager-update-extended',
    templateUrl: './supply-zone-manager-update-extended.component.html'
})
export class SupplyZoneManagerUpdateExtendedComponent extends SupplyZoneManagerUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected supplyZoneService: SupplyZoneExtendedService,
        protected employeeService: EmployeeExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, supplyZoneManagerService, supplyZoneService, employeeService, activatedRoute);
    }
}
