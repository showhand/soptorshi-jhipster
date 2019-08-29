import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    monthlySalaryExtendedPopupRoute,
    monthlySalaryExtendedRoute
} from 'app/entities/monthly-salary-extended/monthly-salary-extended.route';
import {
    MonthlySalaryComponent,
    MonthlySalaryDeleteDialogComponent,
    MonthlySalaryDeletePopupComponent,
    MonthlySalaryDetailComponent,
    MonthlySalaryUpdateComponent
} from 'app/entities/monthly-salary';
import { MonthlySalaryExtendedDetailComponent } from 'app/entities/monthly-salary-extended/monthly-salary-extended-detail.component';
import { MonthlySalaryExtendedComponent } from 'app/entities/monthly-salary-extended/monthly-salary-extended.component';
import { MonthlySalaryExtendedUpdateComponent } from 'app/entities/monthly-salary-extended/monthly-salary-extended-update.component';

const ENTITY_STATES = [...monthlySalaryExtendedRoute, ...monthlySalaryExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MonthlySalaryComponent,
        MonthlySalaryDetailComponent,
        MonthlySalaryUpdateComponent,
        MonthlySalaryExtendedComponent,
        MonthlySalaryExtendedDetailComponent,
        MonthlySalaryExtendedUpdateComponent,
        MonthlySalaryDeleteDialogComponent,
        MonthlySalaryDeletePopupComponent
    ],
    entryComponents: [
        MonthlySalaryExtendedComponent,
        MonthlySalaryExtendedUpdateComponent,
        MonthlySalaryDeleteDialogComponent,
        MonthlySalaryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMonthlySalaryExtendedModule {}
