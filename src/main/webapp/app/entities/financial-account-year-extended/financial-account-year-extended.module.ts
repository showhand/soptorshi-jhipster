import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    FinancialAccountYearExtendedComponent,
    FinancialAccountYearExtendedDetailComponent,
    FinancialAccountYearExtendedUpdateComponent,
    financialAccountYearExtendedRoute,
    financialAccountYearExtendedPopupRoute
} from './';
import {
    FinancialAccountYearComponent,
    FinancialAccountYearDeleteDialogComponent,
    FinancialAccountYearDeletePopupComponent,
    FinancialAccountYearDetailComponent,
    financialAccountYearPopupRoute,
    FinancialAccountYearUpdateComponent
} from 'app/entities/financial-account-year';

const ENTITY_STATES = [...financialAccountYearExtendedRoute, ...financialAccountYearExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FinancialAccountYearComponent,
        FinancialAccountYearDetailComponent,
        FinancialAccountYearUpdateComponent,
        FinancialAccountYearExtendedComponent,
        FinancialAccountYearExtendedDetailComponent,
        FinancialAccountYearExtendedUpdateComponent,
        FinancialAccountYearDeleteDialogComponent,
        FinancialAccountYearDeletePopupComponent
    ],
    entryComponents: [
        FinancialAccountYearExtendedComponent,
        FinancialAccountYearExtendedUpdateComponent,
        FinancialAccountYearDeleteDialogComponent,
        FinancialAccountYearDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiFinancialAccountYearExtendedModule {}
