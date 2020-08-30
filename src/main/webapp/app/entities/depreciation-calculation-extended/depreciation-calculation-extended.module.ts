import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DepreciationCalculationExtendedComponent,
    DepreciationCalculationExtendedDetailComponent,
    DepreciationCalculationExtendedUpdateComponent,
    DepreciationCalculationExtendedDeleteDialogComponent,
    depreciationCalculationExtendedRoute,
    DepreciationCalculationExtendedDeletePopupComponent,
    depreciationCalculationExtendedPopupRoute
} from './';
import {
    DepreciationCalculationComponent,
    DepreciationCalculationDeleteDialogComponent,
    DepreciationCalculationDeletePopupComponent,
    DepreciationCalculationDetailComponent,
    DepreciationCalculationUpdateComponent
} from 'app/entities/depreciation-calculation';

const ENTITY_STATES = [...depreciationCalculationExtendedRoute, ...depreciationCalculationExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DepreciationCalculationComponent,
        DepreciationCalculationDetailComponent,
        DepreciationCalculationUpdateComponent,
        DepreciationCalculationDeleteDialogComponent,
        DepreciationCalculationExtendedComponent,
        DepreciationCalculationExtendedDetailComponent,
        DepreciationCalculationExtendedUpdateComponent,
        DepreciationCalculationExtendedDeleteDialogComponent,
        DepreciationCalculationDeletePopupComponent,
        DepreciationCalculationExtendedComponent,
        DepreciationCalculationExtendedDetailComponent,
        DepreciationCalculationExtendedUpdateComponent,
        DepreciationCalculationExtendedDeleteDialogComponent,
        DepreciationCalculationExtendedDeletePopupComponent
    ],
    entryComponents: [
        DepreciationCalculationExtendedComponent,
        DepreciationCalculationExtendedUpdateComponent,
        DepreciationCalculationExtendedDeleteDialogComponent,
        DepreciationCalculationExtendedDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDepreciationCalculationModule {}
