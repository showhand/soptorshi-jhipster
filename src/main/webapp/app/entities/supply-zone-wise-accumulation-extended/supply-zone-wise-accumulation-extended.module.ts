import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyZoneWiseAccumulationDeleteDialogExtendedComponent,
    SupplyZoneWiseAccumulationDeletePopupExtendedComponent,
    SupplyZoneWiseAccumulationDetailExtendedComponent,
    SupplyZoneWiseAccumulationExtendedComponent,
    supplyZoneWiseAccumulationExtendedRoute,
    supplyZoneWiseAccumulationPopupExtendedRoute,
    SupplyZoneWiseAccumulationUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyZoneWiseAccumulationExtendedRoute, ...supplyZoneWiseAccumulationPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyZoneWiseAccumulationExtendedComponent,
        SupplyZoneWiseAccumulationDetailExtendedComponent,
        SupplyZoneWiseAccumulationUpdateExtendedComponent,
        SupplyZoneWiseAccumulationDeleteDialogExtendedComponent,
        SupplyZoneWiseAccumulationDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyZoneWiseAccumulationExtendedComponent,
        SupplyZoneWiseAccumulationUpdateExtendedComponent,
        SupplyZoneWiseAccumulationDeleteDialogExtendedComponent,
        SupplyZoneWiseAccumulationDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyZoneWiseAccumulationExtendedModule {}
