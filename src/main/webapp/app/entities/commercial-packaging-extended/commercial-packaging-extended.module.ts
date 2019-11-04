import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPackagingDeleteDialogExtendedComponent,
    CommercialPackagingDeletePopupExtendedComponent,
    CommercialPackagingDetailExtendedComponent,
    CommercialPackagingExtendedComponent,
    commercialPackagingExtendedRoute,
    commercialPackagingPopupExtendedRoute,
    CommercialPackagingUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPackagingExtendedRoute, ...commercialPackagingPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPackagingExtendedComponent,
        CommercialPackagingDetailExtendedComponent,
        CommercialPackagingUpdateExtendedComponent,
        CommercialPackagingDeleteDialogExtendedComponent,
        CommercialPackagingDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPackagingExtendedComponent,
        CommercialPackagingUpdateExtendedComponent,
        CommercialPackagingDeleteDialogExtendedComponent,
        CommercialPackagingDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPackagingExtendedModule {}
