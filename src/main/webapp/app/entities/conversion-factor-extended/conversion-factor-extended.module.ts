import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ConversionFactorExtendedComponent,
    ConversionFactorExtendedDetailComponent,
    ConversionFactorExtendedUpdateComponent,
    conversionFactorExtendedRoute,
    conversionFactorExtendedPopupRoute
} from './';
import {
    ConversionFactorComponent,
    ConversionFactorDeleteDialogComponent,
    ConversionFactorDeletePopupComponent,
    ConversionFactorDetailComponent,
    ConversionFactorUpdateComponent
} from 'app/entities/conversion-factor';

const ENTITY_STATES = [...conversionFactorExtendedRoute, ...conversionFactorExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConversionFactorComponent,
        ConversionFactorDetailComponent,
        ConversionFactorUpdateComponent,
        ConversionFactorExtendedComponent,
        ConversionFactorExtendedDetailComponent,
        ConversionFactorExtendedUpdateComponent,
        ConversionFactorDeleteDialogComponent,
        ConversionFactorDeletePopupComponent
    ],
    entryComponents: [
        ConversionFactorExtendedComponent,
        ConversionFactorExtendedUpdateComponent,
        ConversionFactorDeleteDialogComponent,
        ConversionFactorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiConversionFactorModule {}
