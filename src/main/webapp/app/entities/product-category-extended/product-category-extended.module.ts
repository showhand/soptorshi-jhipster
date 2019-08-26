import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { ProductCategoryExtendedDetailComponent } from 'app/entities/product-category-extended/product-category-extended-detail.component';
import { ProductCategoryExtendedComponent } from 'app/entities/product-category-extended/product-category-extended.component';
import {
    productCategoryExtendedPopupRoute,
    productCategoryExtendedRoute
} from 'app/entities/product-category-extended/product-category-extended.route';
import { ProductCategoryExtendedUpdateComponent } from 'app/entities/product-category-extended/product-category-extended-update.component';
import {
    ProductCategoryComponent,
    ProductCategoryDeleteDialogComponent,
    ProductCategoryDeletePopupComponent,
    ProductCategoryDetailComponent,
    ProductCategoryUpdateComponent
} from 'app/entities/product-category';
import { ProductCategoryExtendedCreateComponent } from 'app/entities/product-category-extended/product-category-extended-create.component';
import { ProductExtendedDirectiveComponent } from 'app/entities/product-extended/product-extended-directive.component';
import { SoptorshiProductExtendedModule } from 'app/entities/product-extended/product-extended.module';

const ENTITY_STATES = [...productCategoryExtendedRoute, ...productCategoryExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductCategoryComponent,
        ProductCategoryDetailComponent,
        ProductCategoryUpdateComponent,
        ProductCategoryExtendedComponent,
        ProductCategoryExtendedDetailComponent,
        ProductCategoryExtendedUpdateComponent,
        ProductCategoryExtendedCreateComponent,
        ProductCategoryDeleteDialogComponent,
        ProductCategoryDeletePopupComponent
    ],
    entryComponents: [
        ProductCategoryExtendedComponent,
        ProductCategoryExtendedUpdateComponent,
        ProductCategoryDeleteDialogComponent,
        ProductCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProductCategoryExtendedModule {}
