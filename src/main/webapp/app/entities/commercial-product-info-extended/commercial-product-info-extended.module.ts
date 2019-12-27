import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialProductInfoDeleteDialogExtendedComponent,
    CommercialProductInfoDeletePopupExtendedComponent,
    CommercialProductInfoDetailExtendedComponent,
    CommercialProductInfoExtendedComponent,
    commercialProductInfoExtendedRoute,
    commercialProductInfoPopupExtendedRoute,
    CommercialProductInfoUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialProductInfoExtendedRoute, ...commercialProductInfoPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialProductInfoExtendedComponent,
        CommercialProductInfoDetailExtendedComponent,
        CommercialProductInfoUpdateExtendedComponent,
        CommercialProductInfoDeleteDialogExtendedComponent,
        CommercialProductInfoDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialProductInfoExtendedComponent,
        CommercialProductInfoUpdateExtendedComponent,
        CommercialProductInfoDeleteDialogExtendedComponent,
        CommercialProductInfoDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialProductInfoExtendedModule {}
