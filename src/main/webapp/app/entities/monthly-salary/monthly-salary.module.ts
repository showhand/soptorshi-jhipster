import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    MonthlySalaryComponent,
    MonthlySalaryDetailComponent,
    MonthlySalaryUpdateComponent,
    MonthlySalaryDeletePopupComponent,
    MonthlySalaryDeleteDialogComponent,
    monthlySalaryRoute,
    monthlySalaryPopupRoute
} from './';

const ENTITY_STATES = [...monthlySalaryRoute, ...monthlySalaryPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        MonthlySalaryComponent,
        MonthlySalaryDetailComponent,
        MonthlySalaryUpdateComponent,
        MonthlySalaryDeleteDialogComponent,
        MonthlySalaryDeletePopupComponent
    ],
    entryComponents: [
        MonthlySalaryComponent,
        MonthlySalaryUpdateComponent,
        MonthlySalaryDeleteDialogComponent,
        MonthlySalaryDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMonthlySalaryModule {}
