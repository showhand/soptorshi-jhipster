import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SoptorshiSharedModule } from 'app/shared';
import { BudgetAllocationExtendedComponent } from 'app/entities/budget-allocation-extended/budget-allocation-extended.component';
import { BudgetAllocationExtendedDetailComponent } from 'app/entities/budget-allocation-extended/budget-allocation-extended-detail.component';
import { BudgetAllocationExtendedUpdateExtendedComponent } from 'app/entities/budget-allocation-extended/budget-allocation-extended-update-extended.component';
import { BudgetAllocationDeletePopupComponent, budgetAllocationPopupRoute, budgetAllocationRoute } from 'app/entities/budget-allocation';
import {
    budgetAllocationExtendedPopupRoute,
    budgetAllocationExtendedRoute
} from 'app/entities/budget-allocation-extended/budget-allocation-extended.route';

const ENTITY_STATES = [...budgetAllocationExtendedRoute, ...budgetAllocationExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BudgetAllocationExtendedComponent,
        BudgetAllocationExtendedDetailComponent,
        BudgetAllocationExtendedUpdateExtendedComponent,
        BudgetAllocationDeletePopupComponent
    ],
    entryComponents: [
        BudgetAllocationExtendedComponent,
        BudgetAllocationExtendedDetailComponent,
        BudgetAllocationExtendedUpdateExtendedComponent,
        BudgetAllocationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiBudgetAllocationExtendedModule {}
