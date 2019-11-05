import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPurchaseOrderItemDeleteDialogExtendedComponent,
    CommercialPurchaseOrderItemDeletePopupExtendedComponent,
    CommercialPurchaseOrderItemDetailExtendedComponent,
    CommercialPurchaseOrderItemExtendedComponent,
    commercialPurchaseOrderItemExtendedRoute,
    commercialPurchaseOrderItemPopupExtendedRoute,
    CommercialPurchaseOrderItemUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPurchaseOrderItemExtendedRoute, ...commercialPurchaseOrderItemPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPurchaseOrderItemExtendedComponent,
        CommercialPurchaseOrderItemDetailExtendedComponent,
        CommercialPurchaseOrderItemUpdateExtendedComponent,
        CommercialPurchaseOrderItemDeleteDialogExtendedComponent,
        CommercialPurchaseOrderItemDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPurchaseOrderItemExtendedComponent,
        CommercialPurchaseOrderItemUpdateExtendedComponent,
        CommercialPurchaseOrderItemDeleteDialogExtendedComponent,
        CommercialPurchaseOrderItemDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPurchaseOrderItemExtendedModule {}
