import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyAreaComponent,
    SupplyAreaDetailComponent,
    SupplyAreaUpdateComponent,
    SupplyAreaDeletePopupComponent,
    SupplyAreaDeleteDialogComponent,
    supplyAreaRoute,
    supplyAreaPopupRoute
} from './';

const ENTITY_STATES = [...supplyAreaRoute, ...supplyAreaPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyAreaComponent,
        SupplyAreaDetailComponent,
        SupplyAreaUpdateComponent,
        SupplyAreaDeleteDialogComponent,
        SupplyAreaDeletePopupComponent
    ],
    entryComponents: [SupplyAreaComponent, SupplyAreaUpdateComponent, SupplyAreaDeleteDialogComponent, SupplyAreaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyAreaModule {}
