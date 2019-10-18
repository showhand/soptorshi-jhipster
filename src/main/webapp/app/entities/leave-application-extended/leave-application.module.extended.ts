import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveApplicationComponentExtended,
    LeaveApplicationDetailComponentExtended,
    LeaveApplicationUpdateComponentExtended,
    LeaveApplicationDeletePopupComponentExtended,
    LeaveApplicationDeleteDialogComponentExtended,
    leaveApplicationRouteExtended,
    leaveApplicationPopupRouteExtended
} from './';
import { OthersLeaveApplicationComponent } from './others-leave-application.component';
import { ReviewLeaveApplicationComponent } from './review-leave-application.component';
import { OthersLeaveApplicationHistoryComponent } from './others-leave-application-history.component';

const ENTITY_STATES = [...leaveApplicationRouteExtended, ...leaveApplicationPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveApplicationComponentExtended,
        LeaveApplicationDetailComponentExtended,
        LeaveApplicationUpdateComponentExtended,
        LeaveApplicationDeleteDialogComponentExtended,
        LeaveApplicationDeletePopupComponentExtended,
        OthersLeaveApplicationComponent,
        ReviewLeaveApplicationComponent,
        OthersLeaveApplicationHistoryComponent
    ],
    entryComponents: [
        LeaveApplicationComponentExtended,
        LeaveApplicationUpdateComponentExtended,
        LeaveApplicationDeleteDialogComponentExtended,
        LeaveApplicationDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveApplicationModuleExtended {}
