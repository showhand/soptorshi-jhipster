import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPurchaseOrderDeleteDialogExtendedComponent,
    CommercialPurchaseOrderDeletePopupExtendedComponent,
    CommercialPurchaseOrderDetailExtendedComponent,
    CommercialPurchaseOrderExtendedComponent,
    commercialPurchaseOrderExtendedRoute,
    commercialPurchaseOrderPopupExtendedRoute,
    CommercialPurchaseOrderUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPurchaseOrderExtendedRoute, ...commercialPurchaseOrderPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPurchaseOrderExtendedComponent,
        CommercialPurchaseOrderDetailExtendedComponent,
        CommercialPurchaseOrderUpdateExtendedComponent,
        CommercialPurchaseOrderDeleteDialogExtendedComponent,
        CommercialPurchaseOrderDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPurchaseOrderExtendedComponent,
        CommercialPurchaseOrderUpdateExtendedComponent,
        CommercialPurchaseOrderDeleteDialogExtendedComponent,
        CommercialPurchaseOrderDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPurchaseOrderExtendedModule {}
