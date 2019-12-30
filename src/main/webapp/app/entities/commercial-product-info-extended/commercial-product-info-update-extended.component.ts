import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { CommercialProductInfoExtendedService } from './commercial-product-info-extended.service';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { CommercialProductInfoUpdateComponent } from 'app/entities/commercial-product-info';
import { CommercialBudgetExtendedService } from 'app/entities/commercial-budget-extended';

@Component({
    selector: 'jhi-commercial-product-info-update-extended',
    templateUrl: './commercial-product-info-update-extended.component.html'
})
export class CommercialProductInfoUpdateExtendedComponent extends CommercialProductInfoUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialProductInfoService: CommercialProductInfoExtendedService,
        protected commercialBudgetService: CommercialBudgetExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            commercialProductInfoService,
            commercialBudgetService,
            productCategoryService,
            productService,
            activatedRoute
        );
    }
}
