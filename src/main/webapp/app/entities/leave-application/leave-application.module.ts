import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveApplicationComponent,
    LeaveApplicationDetailComponent,
    LeaveApplicationUpdateComponent,
    LeaveApplicationDeletePopupComponent,
    LeaveApplicationDeleteDialogComponent,
    leaveApplicationRoute,
    leaveApplicationPopupRoute
} from './';
import { OthersLeaveApplicationComponent } from './others-leave-application.component';
import { ReviewLeaveApplicationComponent } from './review-leave-application.component';

const ENTITY_STATES = [...leaveApplicationRoute, ...leaveApplicationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveApplicationComponent,
        LeaveApplicationDetailComponent,
        LeaveApplicationUpdateComponent,
        LeaveApplicationDeleteDialogComponent,
        LeaveApplicationDeletePopupComponent,
        OthersLeaveApplicationComponent,
        ReviewLeaveApplicationComponent
    ],
    entryComponents: [
        LeaveApplicationComponent,
        LeaveApplicationUpdateComponent,
        LeaveApplicationDeleteDialogComponent,
        LeaveApplicationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveApplicationModule {}
