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

const ENTITY_STATES = [...leaveApplicationRoute, ...leaveApplicationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveApplicationComponent,
        LeaveApplicationDetailComponent,
        LeaveApplicationUpdateComponent,
        LeaveApplicationDeleteDialogComponent,
        LeaveApplicationDeletePopupComponent
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
