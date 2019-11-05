import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialWorkOrderDetailsDeleteDialogExtendedComponent,
    CommercialWorkOrderDetailsDeletePopupExtendedComponent,
    CommercialWorkOrderDetailsDetailExtendedComponent,
    CommercialWorkOrderDetailsExtendedComponent,
    commercialWorkOrderDetailsExtendedRoute,
    commercialWorkOrderDetailsPopupExtendedRoute,
    CommercialWorkOrderDetailsUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialWorkOrderDetailsExtendedRoute, ...commercialWorkOrderDetailsPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialWorkOrderDetailsExtendedComponent,
        CommercialWorkOrderDetailsDetailExtendedComponent,
        CommercialWorkOrderDetailsUpdateExtendedComponent,
        CommercialWorkOrderDetailsDeleteDialogExtendedComponent,
        CommercialWorkOrderDetailsDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialWorkOrderDetailsExtendedComponent,
        CommercialWorkOrderDetailsUpdateExtendedComponent,
        CommercialWorkOrderDetailsDeleteDialogExtendedComponent,
        CommercialWorkOrderDetailsDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialWorkOrderDetailsExtendedModule {}
