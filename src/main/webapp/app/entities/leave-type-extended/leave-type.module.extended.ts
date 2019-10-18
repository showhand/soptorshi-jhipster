import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveTypeComponentExtended,
    LeaveTypeDetailComponentExtended,
    LeaveTypeUpdateComponentExtended,
    LeaveTypeDeletePopupComponentExtended,
    LeaveTypeDeleteDialogComponentExtended,
    leaveTypeRouteExtended,
    leaveTypePopupRouteExtended
} from './';

const ENTITY_STATES = [...leaveTypeRouteExtended, ...leaveTypePopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveTypeComponentExtended,
        LeaveTypeDetailComponentExtended,
        LeaveTypeUpdateComponentExtended,
        LeaveTypeDeleteDialogComponentExtended,
        LeaveTypeDeletePopupComponentExtended
    ],
    entryComponents: [
        LeaveTypeComponentExtended,
        LeaveTypeUpdateComponentExtended,
        LeaveTypeDeleteDialogComponentExtended,
        LeaveTypeDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveTypeModuleExtended {}
