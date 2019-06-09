import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LoanManagementComponent,
    LoanManagementDetailComponent,
    LoanManagementUpdateComponent,
    LoanManagementDeletePopupComponent,
    LoanManagementDeleteDialogComponent,
    loanManagementRoute,
    loanManagementPopupRoute
} from './';

const ENTITY_STATES = [...loanManagementRoute, ...loanManagementPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LoanManagementComponent,
        LoanManagementDetailComponent,
        LoanManagementUpdateComponent,
        LoanManagementDeleteDialogComponent,
        LoanManagementDeletePopupComponent
    ],
    entryComponents: [
        LoanManagementComponent,
        LoanManagementUpdateComponent,
        LoanManagementDeleteDialogComponent,
        LoanManagementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLoanManagementModule {}
