import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    MonthlyBalanceComponent,
    MonthlyBalanceDetailComponent,
    MonthlyBalanceUpdateComponent,
    MonthlyBalanceDeletePopupComponent,
    MonthlyBalanceDeleteDialogComponent,
    monthlyBalanceRoute,
    monthlyBalancePopupRoute
} from './';

const ENTITY_STATES = [...monthlyBalanceRoute, ...monthlyBalancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MonthlyBalanceComponent,
        MonthlyBalanceDetailComponent,
        MonthlyBalanceUpdateComponent,
        MonthlyBalanceDeleteDialogComponent,
        MonthlyBalanceDeletePopupComponent
    ],
    entryComponents: [
        MonthlyBalanceComponent,
        MonthlyBalanceUpdateComponent,
        MonthlyBalanceDeleteDialogComponent,
        MonthlyBalanceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMonthlyBalanceModule {}
