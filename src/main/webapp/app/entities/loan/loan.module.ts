import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LoanComponent,
    LoanDetailComponent,
    LoanUpdateComponent,
    LoanDeletePopupComponent,
    LoanDeleteDialogComponent,
    loanRoute,
    loanPopupRoute
} from './';

const ENTITY_STATES = [...loanRoute, ...loanPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LoanComponent, LoanDetailComponent, LoanUpdateComponent, LoanDeleteDialogComponent, LoanDeletePopupComponent],
    entryComponents: [LoanComponent, LoanUpdateComponent, LoanDeleteDialogComponent, LoanDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLoanModule {}
