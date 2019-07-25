import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    BudgetAllocationComponent,
    BudgetAllocationDetailComponent,
    BudgetAllocationUpdateComponent,
    BudgetAllocationDeletePopupComponent,
    BudgetAllocationDeleteDialogComponent,
    budgetAllocationRoute,
    budgetAllocationPopupRoute
} from './';

const ENTITY_STATES = [...budgetAllocationRoute, ...budgetAllocationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        BudgetAllocationComponent,
        BudgetAllocationDetailComponent,
        BudgetAllocationUpdateComponent,
        BudgetAllocationDeleteDialogComponent,
        BudgetAllocationDeletePopupComponent
    ],
    entryComponents: [
        BudgetAllocationComponent,
        BudgetAllocationUpdateComponent,
        BudgetAllocationDeleteDialogComponent,
        BudgetAllocationDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiBudgetAllocationModule {}
