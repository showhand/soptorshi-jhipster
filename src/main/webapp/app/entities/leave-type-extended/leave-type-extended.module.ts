import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveTypeDeleteDialogExtendedComponent,
    LeaveTypeDeletePopupExtendedComponent,
    LeaveTypeDetailExtendedComponent,
    LeaveTypeExtendedComponent,
    leaveTypeExtendedRoute,
    leaveTypePopupExtendedRoute,
    LeaveTypeUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...leaveTypeExtendedRoute, ...leaveTypePopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveTypeExtendedComponent,
        LeaveTypeDetailExtendedComponent,
        LeaveTypeUpdateExtendedComponent,
        LeaveTypeDeleteDialogExtendedComponent,
        LeaveTypeDeletePopupExtendedComponent
    ],
    entryComponents: [
        LeaveTypeExtendedComponent,
        LeaveTypeUpdateExtendedComponent,
        LeaveTypeDeleteDialogExtendedComponent,
        LeaveTypeDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveTypeExtendedModule {}
