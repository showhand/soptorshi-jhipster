import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveTypeComponent,
    LeaveTypeDetailComponent,
    LeaveTypeUpdateComponent,
    LeaveTypeDeletePopupComponent,
    LeaveTypeDeleteDialogComponent,
    leaveTypeRoute,
    leaveTypePopupRoute
} from './';

const ENTITY_STATES = [...leaveTypeRoute, ...leaveTypePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveTypeComponent,
        LeaveTypeDetailComponent,
        LeaveTypeUpdateComponent,
        LeaveTypeDeleteDialogComponent,
        LeaveTypeDeletePopupComponent
    ],
    entryComponents: [LeaveTypeComponent, LeaveTypeUpdateComponent, LeaveTypeDeleteDialogComponent, LeaveTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveTypeModule {}
