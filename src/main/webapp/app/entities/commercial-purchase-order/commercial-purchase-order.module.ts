import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPurchaseOrderComponent,
    CommercialPurchaseOrderDeleteDialogComponent,
    CommercialPurchaseOrderDeletePopupComponent,
    CommercialPurchaseOrderDetailComponent,
    commercialPurchaseOrderPopupRoute,
    commercialPurchaseOrderRoute,
    CommercialPurchaseOrderUpdateComponent
} from './';

const ENTITY_STATES = [...commercialPurchaseOrderRoute, ...commercialPurchaseOrderPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPurchaseOrderComponent,
        CommercialPurchaseOrderDetailComponent,
        CommercialPurchaseOrderUpdateComponent,
        CommercialPurchaseOrderDeleteDialogComponent,
        CommercialPurchaseOrderDeletePopupComponent
    ],
    entryComponents: [
        CommercialPurchaseOrderComponent,
        CommercialPurchaseOrderUpdateComponent,
        CommercialPurchaseOrderDeleteDialogComponent,
        CommercialPurchaseOrderDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPurchaseOrderModule {}
