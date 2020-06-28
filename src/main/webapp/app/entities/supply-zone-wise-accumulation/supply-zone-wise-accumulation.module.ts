import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyZoneWiseAccumulationComponent,
    SupplyZoneWiseAccumulationDeleteDialogComponent,
    SupplyZoneWiseAccumulationDeletePopupComponent,
    SupplyZoneWiseAccumulationDetailComponent,
    supplyZoneWiseAccumulationPopupRoute,
    supplyZoneWiseAccumulationRoute,
    SupplyZoneWiseAccumulationUpdateComponent
} from './';

const ENTITY_STATES = [...supplyZoneWiseAccumulationRoute, ...supplyZoneWiseAccumulationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyZoneWiseAccumulationComponent,
        SupplyZoneWiseAccumulationDetailComponent,
        SupplyZoneWiseAccumulationUpdateComponent,
        SupplyZoneWiseAccumulationDeleteDialogComponent,
        SupplyZoneWiseAccumulationDeletePopupComponent
    ],
    entryComponents: [
        SupplyZoneWiseAccumulationComponent,
        SupplyZoneWiseAccumulationUpdateComponent,
        SupplyZoneWiseAccumulationDeleteDialogComponent,
        SupplyZoneWiseAccumulationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyZoneWiseAccumulationModule {}
