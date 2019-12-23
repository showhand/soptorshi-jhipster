import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPurchaseOrderItemComponent,
    CommercialPurchaseOrderItemDetailComponent,
    CommercialPurchaseOrderItemUpdateComponent,
    CommercialPurchaseOrderItemDeletePopupComponent,
    CommercialPurchaseOrderItemDeleteDialogComponent,
    commercialPurchaseOrderItemRoute,
    commercialPurchaseOrderItemPopupRoute
} from './';

const ENTITY_STATES = [...commercialPurchaseOrderItemRoute, ...commercialPurchaseOrderItemPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPurchaseOrderItemComponent,
        CommercialPurchaseOrderItemDetailComponent,
        CommercialPurchaseOrderItemUpdateComponent,
        CommercialPurchaseOrderItemDeleteDialogComponent,
        CommercialPurchaseOrderItemDeletePopupComponent
    ],
    entryComponents: [
        CommercialPurchaseOrderItemComponent,
        CommercialPurchaseOrderItemUpdateComponent,
        CommercialPurchaseOrderItemDeleteDialogComponent,
        CommercialPurchaseOrderItemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPurchaseOrderItemModule {}
