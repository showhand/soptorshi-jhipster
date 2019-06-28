import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';

import { LeaveBalanceComponent, leaveBalanceRoute } from './';
import { OthersLeaveBalanceComponent } from './others-leave-balance.component';

const ENTITY_STATES = [...leaveBalanceRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LeaveBalanceComponent, OthersLeaveBalanceComponent],
    entryComponents: [LeaveBalanceComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveBalanceModule {}
