import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyChallanExtendedService } from './supply-challan-extended.service';
import { SupplyOrderService } from 'app/entities/supply-order';
import { SupplyChallanUpdateComponent } from 'app/entities/supply-challan';

@Component({
    selector: 'jhi-supply-challan-update-extended',
    templateUrl: './supply-challan-update-extended.component.html'
})
export class SupplyChallanUpdateExtendedComponent extends SupplyChallanUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyChallanService: SupplyChallanExtendedService,
        protected supplyOrderService: SupplyOrderService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, supplyChallanService, supplyOrderService, activatedRoute);
    }
}
