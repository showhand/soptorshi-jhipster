import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPoStatusDeleteDialogExtendedComponent,
    CommercialPoStatusDeletePopupExtendedComponent,
    CommercialPoStatusDetailExtendedComponent,
    CommercialPoStatusExtendedComponent,
    commercialPoStatusExtendedRoute,
    commercialPoStatusPopupExtendedRoute,
    CommercialPoStatusUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPoStatusExtendedRoute, ...commercialPoStatusPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPoStatusExtendedComponent,
        CommercialPoStatusDetailExtendedComponent,
        CommercialPoStatusUpdateExtendedComponent,
        CommercialPoStatusDeleteDialogExtendedComponent,
        CommercialPoStatusDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPoStatusExtendedComponent,
        CommercialPoStatusUpdateExtendedComponent,
        CommercialPoStatusDeleteDialogExtendedComponent,
        CommercialPoStatusDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPoStatusExtendedModule {}
