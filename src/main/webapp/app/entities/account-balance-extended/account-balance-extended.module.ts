import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { BalanceSheetComponent } from './';
import { accountBalanceExtendedRoute } from 'app/entities/account-balance-extended/account-balance-extended.route';
import {
    AccountBalanceComponent,
    AccountBalanceDeleteDialogComponent,
    AccountBalanceDeletePopupComponent,
    AccountBalanceDetailComponent,
    accountBalancePopupRoute,
    AccountBalanceUpdateComponent
} from 'app/entities/account-balance';

const ENTITY_STATES = [...accountBalanceExtendedRoute, ...accountBalancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountBalanceComponent,
        AccountBalanceDetailComponent,
        AccountBalanceUpdateComponent,
        AccountBalanceDeleteDialogComponent,
        AccountBalanceDeletePopupComponent,
        BalanceSheetComponent
    ],
    entryComponents: [
        AccountBalanceComponent,
        AccountBalanceUpdateComponent,
        AccountBalanceDeleteDialogComponent,
        AccountBalanceDeletePopupComponent,
        BalanceSheetComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAccountBalanceModule {}
