import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ConversionFactorComponent,
    ConversionFactorDetailComponent,
    ConversionFactorUpdateComponent,
    ConversionFactorDeletePopupComponent,
    ConversionFactorDeleteDialogComponent,
    conversionFactorRoute,
    conversionFactorPopupRoute
} from './';

const ENTITY_STATES = [...conversionFactorRoute, ...conversionFactorPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConversionFactorComponent,
        ConversionFactorDetailComponent,
        ConversionFactorUpdateComponent,
        ConversionFactorDeleteDialogComponent,
        ConversionFactorDeletePopupComponent
    ],
    entryComponents: [
        ConversionFactorComponent,
        ConversionFactorUpdateComponent,
        ConversionFactorDeleteDialogComponent,
        ConversionFactorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiConversionFactorModule {}
