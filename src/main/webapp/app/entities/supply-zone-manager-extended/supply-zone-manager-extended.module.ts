import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyZoneManagerDeleteDialogExtendedComponent,
    SupplyZoneManagerDeletePopupExtendedComponent,
    SupplyZoneManagerDetailExtendedComponent,
    SupplyZoneManagerExtendedComponent,
    supplyZoneManagerExtendedRoute,
    supplyZoneManagerPopupExtendedRoute,
    SupplyZoneManagerUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyZoneManagerExtendedRoute, ...supplyZoneManagerPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyZoneManagerExtendedComponent,
        SupplyZoneManagerDetailExtendedComponent,
        SupplyZoneManagerUpdateExtendedComponent,
        SupplyZoneManagerDeleteDialogExtendedComponent,
        SupplyZoneManagerDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyZoneManagerExtendedComponent,
        SupplyZoneManagerUpdateExtendedComponent,
        SupplyZoneManagerDeleteDialogExtendedComponent,
        SupplyZoneManagerDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyZoneManagerExtendedModule {}
