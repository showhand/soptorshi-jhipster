import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveTypeDeleteDialogExtendedComponent,
    LeaveTypeDeletePopupComponentExtended,
    LeaveTypeDetailExtendedComponent,
    LeaveTypeExtendedComponent,
    leaveTypeExtendedRoute,
    leaveTypePopupRouteExtended,
    LeaveTypeUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...leaveTypeExtendedRoute, ...leaveTypePopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveTypeExtendedComponent,
        LeaveTypeDetailExtendedComponent,
        LeaveTypeUpdateExtendedComponent,
        LeaveTypeDeleteDialogExtendedComponent,
        LeaveTypeDeletePopupComponentExtended
    ],
    entryComponents: [
        LeaveTypeExtendedComponent,
        LeaveTypeUpdateExtendedComponent,
        LeaveTypeDeleteDialogExtendedComponent,
        LeaveTypeDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveTypeModuleExtended {}
