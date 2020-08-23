import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
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
import { ProfitAndLossComponent } from './profit-and-loss/profit-and-loss.component';
import { FixedAssetComponent } from './fixed-asset/fixed-asset.component';
import { ChangesInEquityComponent } from './changes-in-equity/changes-in-equity.component';
import { OverviewOfDebtComponent } from './overview-of-debt/overview-of-debt.component';
import { CapexDetailsComponent } from './capex-details/capex-details.component';
import { CashFlowComponent } from './cash-flow/cash-flow.component';

const ENTITY_STATES = [...accountBalanceExtendedRoute, ...accountBalancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountBalanceComponent,
        AccountBalanceDetailComponent,
        AccountBalanceUpdateComponent,
        AccountBalanceDeleteDialogComponent,
        AccountBalanceDeletePopupComponent,
        BalanceSheetComponent,
        ProfitAndLossComponent,
        FixedAssetComponent,
        ChangesInEquityComponent,
        OverviewOfDebtComponent,
        CapexDetailsComponent,
        CashFlowComponent
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
