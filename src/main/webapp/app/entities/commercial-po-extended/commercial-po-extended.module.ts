import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPoDeleteDialogExtendedComponent,
    CommercialPoDeletePopupComponent,
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
        CommercialPoDeletePopupComponent
    ],
    entryComponents: [
        CommercialPoExtendedComponent,
        CommercialPoUpdateExtendedComponent,
        CommercialPoDeleteDialogExtendedComponent,
        CommercialPoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPoModule {}
