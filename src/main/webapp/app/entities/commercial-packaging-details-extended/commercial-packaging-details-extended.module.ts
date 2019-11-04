import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPackagingDetailsDeleteDialogExtendedComponent,
    CommercialPackagingDetailsDeletePopupExtendedComponent,
    CommercialPackagingDetailsDetailExtendedComponent,
    CommercialPackagingDetailsExtendedComponent,
    commercialPackagingDetailsExtendedRoute,
    commercialPackagingDetailsPopupExtendedRoute,
    CommercialPackagingDetailsUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPackagingDetailsExtendedRoute, ...commercialPackagingDetailsPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPackagingDetailsExtendedComponent,
        CommercialPackagingDetailsDetailExtendedComponent,
        CommercialPackagingDetailsUpdateExtendedComponent,
        CommercialPackagingDetailsDeleteDialogExtendedComponent,
        CommercialPackagingDetailsDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPackagingDetailsExtendedComponent,
        CommercialPackagingDetailsUpdateExtendedComponent,
        CommercialPackagingDetailsDeleteDialogExtendedComponent,
        CommercialPackagingDetailsDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPackagingDetailsExtendedModule {}
