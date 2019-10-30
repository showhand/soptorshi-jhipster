import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceDeleteDialogExtendedComponent,
    AttendanceDeletePopupComponentExtended,
    AttendanceDetailExtendedComponent,
    AttendanceExtendedComponent,
    attendanceExtendedRoute,
    attendancePopupRouteExtended,
    AttendanceUpdateExtendedComponent
} from './';
import { MyAttendanceComponent } from './my-attendance.component';

const ENTITY_STATES = [...attendanceExtendedRoute, ...attendancePopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceExtendedComponent,
        AttendanceDetailExtendedComponent,
        AttendanceUpdateExtendedComponent,
        AttendanceDeletePopupComponentExtended,
        AttendanceDeleteDialogExtendedComponent,
        MyAttendanceComponent
    ],
    entryComponents: [
        AttendanceExtendedComponent,
        AttendanceUpdateExtendedComponent,
        AttendanceDeleteDialogExtendedComponent,
        AttendanceDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceModuleExtended {}
