import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyAreaManagerComponent,
    SupplyAreaManagerDeleteDialogComponent,
    SupplyAreaManagerDeletePopupComponent,
    SupplyAreaManagerDetailComponent,
    supplyAreaManagerPopupRoute,
    supplyAreaManagerRoute,
    SupplyAreaManagerUpdateComponent
} from './';

const ENTITY_STATES = [...supplyAreaManagerRoute, ...supplyAreaManagerPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyAreaManagerComponent,
        SupplyAreaManagerDetailComponent,
        SupplyAreaManagerUpdateComponent,
        SupplyAreaManagerDeleteDialogComponent,
        SupplyAreaManagerDeletePopupComponent
    ],
    entryComponents: [
        SupplyAreaManagerComponent,
        SupplyAreaManagerUpdateComponent,
        SupplyAreaManagerDeleteDialogComponent,
        SupplyAreaManagerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyAreaManagerModule {}
