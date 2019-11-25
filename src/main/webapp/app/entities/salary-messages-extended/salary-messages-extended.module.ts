import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SalaryMessagesExtendedComponent,
    SalaryMessagesExtendedDetailComponent,
    SalaryMessagesExtendedUpdateComponent,
    salaryMessagesExtendedRoute
} from './';
import {
    SalaryMessagesComponent,
    SalaryMessagesDeleteDialogComponent,
    SalaryMessagesDeletePopupComponent,
    SalaryMessagesDetailComponent,
    salaryMessagesPopupRoute,
    SalaryMessagesUpdateComponent
} from 'app/entities/salary-messages';

const ENTITY_STATES = [...salaryMessagesExtendedRoute, ...salaryMessagesPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SalaryMessagesComponent,
        SalaryMessagesDetailComponent,
        SalaryMessagesUpdateComponent,
        SalaryMessagesExtendedComponent,
        SalaryMessagesExtendedDetailComponent,
        SalaryMessagesExtendedUpdateComponent,
        SalaryMessagesDeleteDialogComponent,
        SalaryMessagesDeletePopupComponent
    ],
    entryComponents: [
        SalaryMessagesExtendedComponent,
        SalaryMessagesExtendedUpdateComponent,
        SalaryMessagesDeleteDialogComponent,
        SalaryMessagesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSalaryMessagesModule {}
