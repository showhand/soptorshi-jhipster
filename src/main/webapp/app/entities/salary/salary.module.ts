import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SalaryComponent,
    SalaryDetailComponent,
    SalaryUpdateComponent,
    SalaryDeletePopupComponent,
    SalaryDeleteDialogComponent,
    salaryRoute,
    salaryPopupRoute
} from './';

const ENTITY_STATES = [...salaryRoute, ...salaryPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    // declarations: [SalaryComponent, SalaryDetailComponent, SalaryUpdateComponent, SalaryDeleteDialogComponent, SalaryDeletePopupComponent],
    // entryComponents: [SalaryComponent, SalaryUpdateComponent, SalaryDeleteDialogComponent, SalaryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSalaryModule {}
