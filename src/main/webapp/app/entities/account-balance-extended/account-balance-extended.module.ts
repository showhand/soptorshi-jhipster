import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AccountBalanceComponent,
    AccountBalanceDetailComponent,
    AccountBalanceUpdateComponent,
    AccountBalanceDeletePopupComponent,
    AccountBalanceDeleteDialogComponent,
    accountBalanceExtendedRoute,
    accountBalancePopupRoute
} from './';

const ENTITY_STATES = [...accountBalanceExtendedRoute, ...accountBalancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountBalanceComponent,
        AccountBalanceDetailComponent,
        AccountBalanceUpdateComponent,
        AccountBalanceDeleteDialogComponent,
        AccountBalanceDeletePopupComponent
    ],
    entryComponents: [
        AccountBalanceComponent,
        AccountBalanceUpdateComponent,
        AccountBalanceDeleteDialogComponent,
        AccountBalanceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAccountBalanceModule {}
