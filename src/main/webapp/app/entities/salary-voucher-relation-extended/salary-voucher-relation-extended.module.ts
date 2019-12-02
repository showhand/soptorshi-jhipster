import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SalaryVoucherRelationExtendedComponent,
    SalaryVoucherRelationExtendedDetailComponent,
    SalaryVoucherRelationExtendedUpdateComponent,
    salaryVoucherRelationExtendedRoute
} from './';
import {
    SalaryVoucherRelationComponent,
    SalaryVoucherRelationDeleteDialogComponent,
    SalaryVoucherRelationDeletePopupComponent,
    SalaryVoucherRelationDetailComponent,
    salaryVoucherRelationPopupRoute,
    SalaryVoucherRelationUpdateComponent
} from 'app/entities/salary-voucher-relation';

const ENTITY_STATES = [...salaryVoucherRelationExtendedRoute, ...salaryVoucherRelationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SalaryVoucherRelationComponent,
        SalaryVoucherRelationDetailComponent,
        SalaryVoucherRelationUpdateComponent,
        SalaryVoucherRelationExtendedComponent,
        SalaryVoucherRelationExtendedDetailComponent,
        SalaryVoucherRelationExtendedUpdateComponent,
        SalaryVoucherRelationDeleteDialogComponent,
        SalaryVoucherRelationDeletePopupComponent
    ],
    entryComponents: [
        SalaryVoucherRelationExtendedComponent,
        SalaryVoucherRelationExtendedUpdateComponent,
        SalaryVoucherRelationDeleteDialogComponent,
        SalaryVoucherRelationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSalaryVoucherRelationModule {}
