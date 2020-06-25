import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyZoneComponent,
    SupplyZoneDeleteDialogComponent,
    SupplyZoneDeletePopupComponent,
    SupplyZoneDetailComponent,
    supplyZonePopupRoute,
    supplyZoneRoute,
    SupplyZoneUpdateComponent
} from './';

const ENTITY_STATES = [...supplyZoneRoute, ...supplyZonePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyZoneComponent,
        SupplyZoneDetailComponent,
        SupplyZoneUpdateComponent,
        SupplyZoneDeleteDialogComponent,
        SupplyZoneDeletePopupComponent
    ],
    entryComponents: [SupplyZoneComponent, SupplyZoneUpdateComponent, SupplyZoneDeleteDialogComponent, SupplyZoneDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyZoneModule {}
