import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PayrollManagementComponent,
    PayrollManagementDetailComponent,
    PayrollManagementUpdateComponent,
    PayrollManagementDeletePopupComponent,
    PayrollManagementDeleteDialogComponent,
    payrollManagementRoute,
    payrollManagementPopupRoute
} from './';

const ENTITY_STATES = [...payrollManagementRoute, ...payrollManagementPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PayrollManagementComponent,
        PayrollManagementDetailComponent,
        PayrollManagementUpdateComponent,
        PayrollManagementDeleteDialogComponent,
        PayrollManagementDeletePopupComponent
    ],
    entryComponents: [
        PayrollManagementComponent,
        PayrollManagementUpdateComponent,
        PayrollManagementDeleteDialogComponent,
        PayrollManagementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPayrollManagementModule {}
