import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyAreaManagerExtendedService } from './supply-area-manager-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyAreaService } from 'app/entities/supply-area';
import { EmployeeService } from 'app/entities/employee';
import { SupplyAreaManagerUpdateComponent } from 'app/entities/supply-area-manager';

@Component({
    selector: 'jhi-supply-area-manager-update-extended',
    templateUrl: './supply-area-manager-update-extended.component.html'
})
export class SupplyAreaManagerUpdateExtendedComponent extends SupplyAreaManagerUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyAreaService: SupplyAreaService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, supplyAreaManagerService, supplyZoneService, supplyAreaService, employeeService, activatedRoute);
    }
}
