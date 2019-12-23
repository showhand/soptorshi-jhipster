import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyZoneComponent,
    SupplyZoneDetailComponent,
    SupplyZoneUpdateComponent,
    SupplyZoneDeletePopupComponent,
    SupplyZoneDeleteDialogComponent,
    supplyZoneRoute,
    supplyZonePopupRoute
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
