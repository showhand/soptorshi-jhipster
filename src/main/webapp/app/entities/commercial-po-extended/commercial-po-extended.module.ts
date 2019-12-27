import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPoDeleteDialogExtendedComponent,
    CommercialPoDeletePopupExtendedComponent,
    CommercialPoDetailExtendedComponent,
    CommercialPoExtendedComponent,
    commercialPoExtendedRoute,
    commercialPoPopupExtendedRoute,
    CommercialPoUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPoExtendedRoute, ...commercialPoPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPoExtendedComponent,
        CommercialPoDetailExtendedComponent,
        CommercialPoUpdateExtendedComponent,
        CommercialPoDeleteDialogExtendedComponent,
        CommercialPoDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPoExtendedComponent,
        CommercialPoUpdateExtendedComponent,
        CommercialPoDeleteDialogExtendedComponent,
        CommercialPoDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPoExtendedModule {}
