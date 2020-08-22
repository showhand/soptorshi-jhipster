import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DepreciationCalculationComponent,
    DepreciationCalculationDetailComponent,
    DepreciationCalculationUpdateComponent,
    DepreciationCalculationDeletePopupComponent,
    DepreciationCalculationDeleteDialogComponent,
    depreciationCalculationRoute,
    depreciationCalculationPopupRoute
} from './';

const ENTITY_STATES = [...depreciationCalculationRoute, ...depreciationCalculationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        DepreciationCalculationExtendedComponent,
        DepreciationCalculationExtendedDetailComponent,
        DepreciationCalculationExtendedUpdateComponent,
        DepreciationCalculationExtendedDeleteDialogComponent,
        DepreciationCalculationDeletePopupComponent
    ],
    entryComponents: [
        DepreciationCalculationExtendedComponent,
        DepreciationCalculationExtendedUpdateComponent,
        DepreciationCalculationExtendedDeleteDialogComponent,
        DepreciationCalculationDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDepreciationCalculationModule {}
