import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialBudgetComponent,
    CommercialBudgetDeleteDialogComponent,
    CommercialBudgetDeletePopupComponent,
    CommercialBudgetDetailComponent,
    commercialBudgetPopupRoute,
    commercialBudgetRoute,
    CommercialBudgetUpdateComponent
} from './';

const ENTITY_STATES = [...commercialBudgetRoute, ...commercialBudgetPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialBudgetComponent,
        CommercialBudgetDetailComponent,
        CommercialBudgetUpdateComponent,
        CommercialBudgetDeleteDialogComponent,
        CommercialBudgetDeletePopupComponent
    ],
    entryComponents: [
        CommercialBudgetComponent,
        CommercialBudgetUpdateComponent,
        CommercialBudgetDeleteDialogComponent,
        CommercialBudgetDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialBudgetModule {}
