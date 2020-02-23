import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveApplicationDeleteDialogExtendedComponent,
    LeaveApplicationDeletePopupExtendedComponent,
    LeaveApplicationDetailExtendedComponent,
    LeaveApplicationExtendedComponent,
    leaveApplicationExtendedRoute,
    leaveApplicationPopupExtendedRoute,
    LeaveApplicationUpdateExtendedComponent,
    ReviewLeaveApplicationComponent
} from './';
import { OthersLeaveApplicationComponent } from 'app/entities/leave-application-extended/others-leave-application.component';
import { OthersLeaveApplicationHistoryComponent } from 'app/entities/leave-application-extended/others-leave-application-history.component';

const ENTITY_STATES = [...leaveApplicationExtendedRoute, ...leaveApplicationPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveApplicationExtendedComponent,
        LeaveApplicationDetailExtendedComponent,
        LeaveApplicationUpdateExtendedComponent,
        LeaveApplicationDeleteDialogExtendedComponent,
        LeaveApplicationDeletePopupExtendedComponent,
        OthersLeaveApplicationComponent,
        OthersLeaveApplicationHistoryComponent,
        ReviewLeaveApplicationComponent
    ],
    entryComponents: [
        LeaveApplicationExtendedComponent,
        LeaveApplicationUpdateExtendedComponent,
        LeaveApplicationDeleteDialogExtendedComponent,
        LeaveApplicationDeletePopupExtendedComponent,
        OthersLeaveApplicationComponent,
        OthersLeaveApplicationHistoryComponent,
        ReviewLeaveApplicationComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveApplicationExtendedModule {}
