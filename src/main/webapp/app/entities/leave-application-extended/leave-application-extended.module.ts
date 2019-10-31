import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveApplicationDeleteDialogExtendedComponent,
    LeaveApplicationDeletePopupExtendedComponent,
    LeaveApplicationDetailExtendedComponent,
    LeaveApplicationExtendedComponent,
    leaveApplicationExtendedRoute,
    leaveApplicationPopupRouteExtended,
    LeaveApplicationUpdateExtendedComponent
} from './';
import { OthersLeaveApplicationComponent } from './others-leave-application.component';
import { ReviewLeaveApplicationComponent } from './review-leave-application.component';
import { OthersLeaveApplicationHistoryComponent } from './others-leave-application-history.component';

const ENTITY_STATES = [...leaveApplicationExtendedRoute, ...leaveApplicationPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveApplicationExtendedComponent,
        LeaveApplicationDetailExtendedComponent,
        LeaveApplicationUpdateExtendedComponent,
        LeaveApplicationDeleteDialogExtendedComponent,
        LeaveApplicationDeletePopupExtendedComponent,
        OthersLeaveApplicationComponent,
        ReviewLeaveApplicationComponent,
        OthersLeaveApplicationHistoryComponent
    ],
    entryComponents: [
        LeaveApplicationExtendedComponent,
        LeaveApplicationUpdateExtendedComponent,
        LeaveApplicationDeleteDialogExtendedComponent,
        LeaveApplicationDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveApplicationModuleExtended {}
