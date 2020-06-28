import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyZoneWiseAccumulationExtendedService } from './supply-zone-wise-accumulation-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { SupplyZoneWiseAccumulationUpdateComponent } from 'app/entities/supply-zone-wise-accumulation';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-update-extended',
    templateUrl: './supply-zone-wise-accumulation-update-extended.component.html'
})
export class SupplyZoneWiseAccumulationUpdateExtendedComponent extends SupplyZoneWiseAccumulationUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyZoneWiseAccumulationService: SupplyZoneWiseAccumulationExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyZoneWiseAccumulationService,
            supplyZoneService,
            supplyZoneManagerService,
            productCategoryService,
            productService,
            activatedRoute
        );
    }
}
