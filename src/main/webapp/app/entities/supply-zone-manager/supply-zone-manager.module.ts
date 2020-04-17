import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyZoneManagerComponent,
    SupplyZoneManagerDeleteDialogComponent,
    SupplyZoneManagerDeletePopupComponent,
    SupplyZoneManagerDetailComponent,
    supplyZoneManagerPopupRoute,
    supplyZoneManagerRoute,
    SupplyZoneManagerUpdateComponent
} from './';

const ENTITY_STATES = [...supplyZoneManagerRoute, ...supplyZoneManagerPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyZoneManagerComponent,
        SupplyZoneManagerDetailComponent,
        SupplyZoneManagerUpdateComponent,
        SupplyZoneManagerDeleteDialogComponent,
        SupplyZoneManagerDeletePopupComponent
    ],
    entryComponents: [
        SupplyZoneManagerComponent,
        SupplyZoneManagerUpdateComponent,
        SupplyZoneManagerDeleteDialogComponent,
        SupplyZoneManagerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyZoneManagerModule {}
