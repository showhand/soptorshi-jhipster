import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ProductPriceComponent,
    ProductPriceDetailComponent,
    ProductPriceUpdateComponent,
    ProductPriceDeletePopupComponent,
    ProductPriceDeleteDialogComponent,
    productPriceRoute,
    productPricePopupRoute
} from './';

const ENTITY_STATES = [...productPriceRoute, ...productPricePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductPriceComponent,
        ProductPriceDetailComponent,
        ProductPriceUpdateComponent,
        ProductPriceDeleteDialogComponent,
        ProductPriceDeletePopupComponent
    ],
    entryComponents: [
        ProductPriceComponent,
        ProductPriceUpdateComponent,
        ProductPriceDeleteDialogComponent,
        ProductPriceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProductPriceModule {}
