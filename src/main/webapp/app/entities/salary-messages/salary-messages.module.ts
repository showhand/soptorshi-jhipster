import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SalaryMessagesComponent,
    SalaryMessagesDetailComponent,
    SalaryMessagesUpdateComponent,
    SalaryMessagesDeletePopupComponent,
    SalaryMessagesDeleteDialogComponent,
    salaryMessagesRoute,
    salaryMessagesPopupRoute
} from './';

const ENTITY_STATES = [...salaryMessagesRoute, ...salaryMessagesPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        SalaryMessagesComponent,
        SalaryMessagesDetailComponent,
        SalaryMessagesUpdateComponent,
        SalaryMessagesDeleteDialogComponent,
        SalaryMessagesDeletePopupComponent
    ],
    entryComponents: [
        SalaryMessagesComponent,
        SalaryMessagesUpdateComponent,
        SalaryMessagesDeleteDialogComponent,
        SalaryMessagesDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSalaryMessagesModule {}
