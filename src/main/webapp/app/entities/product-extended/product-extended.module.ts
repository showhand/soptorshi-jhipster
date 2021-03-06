import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';

import {
    ProductComponent,
    ProductDeleteDialogComponent,
    ProductDeletePopupComponent,
    ProductDetailComponent,
    ProductUpdateComponent
} from 'app/entities/product';
import { ProductExtendedUpdateComponent } from 'app/entities/product-extended/product-extended-update.component';
import { ProductExtendedComponent } from 'app/entities/product-extended/product-extended.component';
import { ProductExtendedDetailComponent } from 'app/entities/product-extended/product-extended-detail.component';
import { productExtendedPopupRoute, productExtendedRoute } from 'app/entities/product-extended/product-extended.route';
import { ProductExtendedDirectiveComponent } from 'app/entities/product-extended/product-extended-directive.component';
import { ProductExtendedCategoryWiseComponent } from 'app/entities/product-extended/product-extended-category-wise.component';

const ENTITY_STATES = [...productExtendedRoute, ...productExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductComponent,
        ProductDetailComponent,
        ProductUpdateComponent,
        ProductExtendedComponent,
        ProductExtendedDetailComponent,
        ProductExtendedUpdateComponent,
        ProductDeleteDialogComponent,
        ProductDeletePopupComponent,
        ProductExtendedDirectiveComponent,
        ProductExtendedCategoryWiseComponent
    ],
    entryComponents: [ProductExtendedComponent, ProductExtendedUpdateComponent, ProductDeleteDialogComponent, ProductDeletePopupComponent],
    exports: [
        ProductExtendedComponent,
        ProductExtendedDetailComponent,
        ProductExtendedUpdateComponent,
        ProductDeleteDialogComponent,
        ProductDeletePopupComponent,
        ProductExtendedDirectiveComponent,
        ProductExtendedCategoryWiseComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProductExtendedModule {}
