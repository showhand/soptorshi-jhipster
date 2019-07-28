import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceComponent,
    AttendanceDetailComponent,
    AttendanceUpdateComponent,
    AttendanceDeletePopupComponent,
    AttendanceDeleteDialogComponent,
    attendanceRoute,
    attendancePopupRoute
} from './';
import { MyAttendanceComponent } from './my-attendance.component';

const ENTITY_STATES = [...attendanceRoute, ...attendancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceComponent,
        AttendanceDetailComponent,
        AttendanceUpdateComponent,
        AttendanceDeleteDialogComponent,
        AttendanceDeletePopupComponent,
        MyAttendanceComponent
    ],
    entryComponents: [AttendanceComponent, AttendanceUpdateComponent, AttendanceDeleteDialogComponent, AttendanceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceModule {}
