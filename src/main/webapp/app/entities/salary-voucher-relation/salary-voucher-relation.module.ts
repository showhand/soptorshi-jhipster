import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SalaryVoucherRelationComponent,
    SalaryVoucherRelationDetailComponent,
    SalaryVoucherRelationUpdateComponent,
    SalaryVoucherRelationDeletePopupComponent,
    SalaryVoucherRelationDeleteDialogComponent,
    salaryVoucherRelationRoute,
    salaryVoucherRelationPopupRoute
} from './';

const ENTITY_STATES = [...salaryVoucherRelationRoute, ...salaryVoucherRelationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SalaryVoucherRelationComponent,
        SalaryVoucherRelationDetailComponent,
        SalaryVoucherRelationUpdateComponent,
        SalaryVoucherRelationDeleteDialogComponent,
        SalaryVoucherRelationDeletePopupComponent
    ],
    entryComponents: [
        SalaryVoucherRelationComponent,
        SalaryVoucherRelationUpdateComponent,
        SalaryVoucherRelationDeleteDialogComponent,
        SalaryVoucherRelationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSalaryVoucherRelationModule {}
