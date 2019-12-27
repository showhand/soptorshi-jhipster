import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { CommercialProductInfoExtendedService } from './commercial-product-info-extended.service';
import { CommercialBudgetService } from 'app/entities/commercial-budget';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { CommercialProductInfoUpdateComponent } from 'app/entities/commercial-product-info';

@Component({
    selector: 'jhi-commercial-product-info-update-extended',
    templateUrl: './commercial-product-info-update-extended.component.html'
})
export class CommercialProductInfoUpdateExtendedComponent extends CommercialProductInfoUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialProductInfoService: CommercialProductInfoExtendedService,
        protected commercialBudgetService: CommercialBudgetService,
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
