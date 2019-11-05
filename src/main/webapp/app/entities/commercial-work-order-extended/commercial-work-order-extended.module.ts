import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialWorkOrderDeleteDialogExtendedComponent,
    CommercialWorkOrderDeletePopupExtendedComponent,
    CommercialWorkOrderDetailExtendedComponent,
    CommercialWorkOrderExtendedComponent,
    commercialWorkOrderExtendedRoute,
    commercialWorkOrderPopupExtendedRoute,
    CommercialWorkOrderUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialWorkOrderExtendedRoute, ...commercialWorkOrderPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialWorkOrderExtendedComponent,
        CommercialWorkOrderDetailExtendedComponent,
        CommercialWorkOrderUpdateExtendedComponent,
        CommercialWorkOrderDeleteDialogExtendedComponent,
        CommercialWorkOrderDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialWorkOrderExtendedComponent,
        CommercialWorkOrderUpdateExtendedComponent,
        CommercialWorkOrderDeleteDialogExtendedComponent,
        CommercialWorkOrderDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialWorkOrderExtendedModule {}
