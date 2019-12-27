import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialBudgetDeleteDialogExtendedComponent,
    CommercialBudgetDeletePopupExtendedComponent,
    CommercialBudgetDetailExtendedComponent,
    CommercialBudgetExtendedComponent,
    commercialBudgetExtendedRoute,
    commercialBudgetPopupExtendedRoute,
    CommercialBudgetUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialBudgetExtendedRoute, ...commercialBudgetPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialBudgetExtendedComponent,
        CommercialBudgetDetailExtendedComponent,
        CommercialBudgetUpdateExtendedComponent,
        CommercialBudgetDeleteDialogExtendedComponent,
        CommercialBudgetDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialBudgetExtendedComponent,
        CommercialBudgetUpdateExtendedComponent,
        CommercialBudgetDeleteDialogExtendedComponent,
        CommercialBudgetDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialBudgetExtendedModule {}
