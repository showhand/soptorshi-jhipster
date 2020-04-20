import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyZoneDeleteDialogExtendedComponent,
    SupplyZoneDeletePopupExtendedComponent,
    SupplyZoneDetailExtendedComponent,
    SupplyZoneExtendedComponent,
    supplyZoneExtendedRoute,
    supplyZonePopupExtendedRoute,
    SupplyZoneUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyZoneExtendedRoute, ...supplyZonePopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyZoneExtendedComponent,
        SupplyZoneDetailExtendedComponent,
        SupplyZoneUpdateExtendedComponent,
        SupplyZoneDeleteDialogExtendedComponent,
        SupplyZoneDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyZoneExtendedComponent,
        SupplyZoneUpdateExtendedComponent,
        SupplyZoneDeleteDialogExtendedComponent,
        SupplyZoneDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyZoneExtendedModule {}
