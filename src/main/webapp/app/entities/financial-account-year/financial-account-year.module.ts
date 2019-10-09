import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    FinancialAccountYearComponent,
    FinancialAccountYearDetailComponent,
    FinancialAccountYearUpdateComponent,
    FinancialAccountYearDeletePopupComponent,
    FinancialAccountYearDeleteDialogComponent,
    financialAccountYearRoute,
    financialAccountYearPopupRoute
} from './';

const ENTITY_STATES = [...financialAccountYearRoute, ...financialAccountYearPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*    declarations: [
        FinancialAccountYearComponent,
        FinancialAccountYearDetailComponent,
        FinancialAccountYearUpdateComponent,
        FinancialAccountYearDeleteDialogComponent,
        FinancialAccountYearDeletePopupComponent
    ],
    entryComponents: [
        FinancialAccountYearComponent,
        FinancialAccountYearUpdateComponent,
        FinancialAccountYearDeleteDialogComponent,
        FinancialAccountYearDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiFinancialAccountYearModule {}
