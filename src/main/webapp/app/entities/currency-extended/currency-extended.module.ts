import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CurrencyExtendedComponent,
    CurrencyExtendedDetailComponent,
    currencyExtendedPopupRoute,
    currencyExtendedRoute,
    CurrencyExtendedUpdateComponent
} from './';
import {
    CurrencyComponent,
    CurrencyDeleteDialogComponent,
    CurrencyDeletePopupComponent,
    CurrencyDetailComponent,
    CurrencyUpdateComponent
} from 'app/entities/currency';

const ENTITY_STATES = [...currencyExtendedRoute, ...currencyExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CurrencyComponent,
        CurrencyDetailComponent,
        CurrencyUpdateComponent,
        CurrencyExtendedComponent,
        CurrencyExtendedDetailComponent,
        CurrencyExtendedUpdateComponent,
        CurrencyDeleteDialogComponent,
        CurrencyDeletePopupComponent
    ],
    entryComponents: [
        CurrencyExtendedComponent,
        CurrencyExtendedUpdateComponent,
        CurrencyDeleteDialogComponent,
        CurrencyDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCurrencyModule {}
