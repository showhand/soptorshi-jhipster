import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPurchaseOrderItemComponent,
    CommercialPurchaseOrderItemDeleteDialogComponent,
    CommercialPurchaseOrderItemDeletePopupExtendedComponent,
    CommercialPurchaseOrderItemDetailComponent,
    commercialPurchaseOrderItemPopupRoute,
    commercialPurchaseOrderItemRoute,
    CommercialPurchaseOrderItemUpdateComponent
} from './';

const ENTITY_STATES = [...commercialPurchaseOrderItemRoute, ...commercialPurchaseOrderItemPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPurchaseOrderItemComponent,
        CommercialPurchaseOrderItemDetailComponent,
        CommercialPurchaseOrderItemUpdateComponent,
        CommercialPurchaseOrderItemDeleteDialogComponent,
        CommercialPurchaseOrderItemDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPurchaseOrderItemComponent,
        CommercialPurchaseOrderItemUpdateComponent,
        CommercialPurchaseOrderItemDeleteDialogComponent,
        CommercialPurchaseOrderItemDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPurchaseOrderItemModule {}
