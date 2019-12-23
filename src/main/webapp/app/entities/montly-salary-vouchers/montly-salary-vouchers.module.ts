import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    MontlySalaryVouchersComponent,
    MontlySalaryVouchersDetailComponent,
    MontlySalaryVouchersUpdateComponent,
    MontlySalaryVouchersDeletePopupComponent,
    MontlySalaryVouchersDeleteDialogComponent,
    montlySalaryVouchersRoute,
    montlySalaryVouchersPopupRoute
} from './';

const ENTITY_STATES = [...montlySalaryVouchersRoute, ...montlySalaryVouchersPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MontlySalaryVouchersComponent,
        MontlySalaryVouchersDetailComponent,
        MontlySalaryVouchersUpdateComponent,
        MontlySalaryVouchersDeleteDialogComponent,
        MontlySalaryVouchersDeletePopupComponent
    ],
    entryComponents: [
        MontlySalaryVouchersComponent,
        MontlySalaryVouchersUpdateComponent,
        MontlySalaryVouchersDeleteDialogComponent,
        MontlySalaryVouchersDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMontlySalaryVouchersModule {}
