import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceComponentExtended,
    AttendanceDetailComponentExtended,
    AttendanceUpdateComponentExtended,
    AttendanceDeletePopupComponentExtended,
    AttendanceDeleteDialogComponentExtended,
    attendanceRouteExtended,
    attendancePopupRouteExtended
} from './';
import { MyAttendanceComponent } from './my-attendance.component';

const ENTITY_STATES = [...attendanceRouteExtended, ...attendancePopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceComponentExtended,
        AttendanceDetailComponentExtended,
        AttendanceUpdateComponentExtended,
        AttendanceDeletePopupComponentExtended,
        AttendanceDeleteDialogComponentExtended,
        MyAttendanceComponent
    ],
    entryComponents: [
        AttendanceComponentExtended,
        AttendanceUpdateComponentExtended,
        AttendanceDeleteDialogComponentExtended,
        AttendanceDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceModuleExtended {}
