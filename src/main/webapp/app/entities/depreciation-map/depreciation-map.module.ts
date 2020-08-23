import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DepreciationMapComponent,
    DepreciationMapDetailComponent,
    DepreciationMapUpdateComponent,
    DepreciationMapDeletePopupComponent,
    DepreciationMapDeleteDialogComponent,
    depreciationMapRoute,
    depreciationMapPopupRoute
} from './';

const ENTITY_STATES = [...depreciationMapRoute, ...depreciationMapPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DepreciationMapComponent,
        DepreciationMapDetailComponent,
        DepreciationMapUpdateComponent,
        DepreciationMapDeleteDialogComponent,
        DepreciationMapDeletePopupComponent
    ],
    entryComponents: [
        DepreciationMapComponent,
        DepreciationMapUpdateComponent,
        DepreciationMapDeleteDialogComponent,
        DepreciationMapDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDepreciationMapModule {}
