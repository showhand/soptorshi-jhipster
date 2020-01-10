import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceDeleteDialogExtendedComponent,
    AttendanceDeletePopupExtendedComponent,
    AttendanceDetailExtendedComponent,
    AttendanceExtendedComponent,
    attendanceExtendedRoute,
    attendancePopupExtendedRoute,
    AttendanceUpdateExtendedComponent
} from './';
import { MyAttendanceComponent } from './my-attendance.component';

const ENTITY_STATES = [...attendanceExtendedRoute, ...attendancePopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceExtendedComponent,
        AttendanceDetailExtendedComponent,
        AttendanceUpdateExtendedComponent,
        AttendanceDeletePopupExtendedComponent,
        AttendanceDeleteDialogExtendedComponent,
        MyAttendanceComponent
    ],
    entryComponents: [
        AttendanceExtendedComponent,
        AttendanceUpdateExtendedComponent,
        AttendanceDeleteDialogExtendedComponent,
        AttendanceDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceExtendedModule {}
