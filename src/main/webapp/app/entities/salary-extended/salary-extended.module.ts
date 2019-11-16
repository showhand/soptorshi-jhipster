import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SalaryExtendedComponent,
    SalaryExtendedDetailComponent,
    SalaryExtendedUpdateComponent,
    SalaryExtendedDeleteDialogComponent,
    salaryExtendedRoute,
    SalaryExtendedDeletePopupComponent,
    salaryExtendedPopupRoute
} from './';
import {
    SalaryComponent,
    SalaryDeleteDialogComponent,
    SalaryDeletePopupComponent,
    SalaryDetailComponent,
    SalaryUpdateComponent
} from 'app/entities/salary';

const ENTITY_STATES = [...salaryExtendedRoute, ...salaryExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SalaryComponent,
        SalaryDetailComponent,
        SalaryUpdateComponent,
        SalaryDeleteDialogComponent,
        SalaryDeletePopupComponent,
        SalaryExtendedComponent,
        SalaryExtendedDetailComponent,
        SalaryExtendedUpdateComponent,
        SalaryExtendedDeleteDialogComponent,
        SalaryExtendedDeletePopupComponent
    ],
    entryComponents: [
        SalaryExtendedComponent,
        SalaryExtendedUpdateComponent,
        SalaryExtendedDeleteDialogComponent,
        SalaryExtendedDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSalaryModule {}
