import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { ProductionExtendedService } from './production-extended.service';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { RequisitionService } from 'app/entities/requisition';
import { ProductionUpdateComponent } from 'app/entities/production';

@Component({
    selector: 'jhi-production-update-extended',
    templateUrl: './production-update-extended.component.html'
})
export class ProductionUpdateExtendedComponent extends ProductionUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productionService: ProductionExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, productionService, productCategoryService, productService, requisitionService, activatedRoute);
    }
}
