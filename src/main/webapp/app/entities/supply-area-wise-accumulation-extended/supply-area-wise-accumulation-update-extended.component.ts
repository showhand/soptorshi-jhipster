import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyAreaWiseAccumulationExtendedService } from './supply-area-wise-accumulation-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { SupplyAreaService } from 'app/entities/supply-area';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { SupplyAreaWiseAccumulationUpdateComponent } from 'app/entities/supply-area-wise-accumulation';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-update-extended',
    templateUrl: './supply-area-wise-accumulation-update-extended.component.html'
})
export class SupplyAreaWiseAccumulationUpdateExtendedComponent extends SupplyAreaWiseAccumulationUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyAreaWiseAccumulationService: SupplyAreaWiseAccumulationExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected supplyAreaService: SupplyAreaService,
        protected supplyAreaManagerService: SupplyAreaManagerService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyAreaWiseAccumulationService,
            supplyZoneService,
            supplyZoneManagerService,
            supplyAreaService,
            supplyAreaManagerService,
            productCategoryService,
            productService,
            activatedRoute
        );
    }
}
