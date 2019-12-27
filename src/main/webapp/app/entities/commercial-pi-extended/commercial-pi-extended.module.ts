import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPiDeleteDialogExtendedComponent,
    CommercialPiDeletePopupExtendedComponent,
    CommercialPiDetailExtendedComponent,
    CommercialPiExtendedComponent,
    commercialPiExtendedRoute,
    commercialPiPopupExtendedRoute,
    CommercialPiUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialPiExtendedRoute, ...commercialPiPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPiExtendedComponent,
        CommercialPiDetailExtendedComponent,
        CommercialPiUpdateExtendedComponent,
        CommercialPiDeleteDialogExtendedComponent,
        CommercialPiDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialPiExtendedComponent,
        CommercialPiUpdateExtendedComponent,
        CommercialPiDeleteDialogExtendedComponent,
        CommercialPiDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPiExtendedModule {}
