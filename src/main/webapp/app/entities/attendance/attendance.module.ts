import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceComponent,
    AttendanceDeleteDialogComponent,
    AttendanceDeletePopupComponent,
    AttendanceDetailComponent,
    attendancePopupRoute,
    attendanceRoute,
    AttendanceUpdateComponent
} from './';

const ENTITY_STATES = [...attendanceRoute, ...attendancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceComponent,
        AttendanceDetailComponent,
        AttendanceUpdateComponent,
        AttendanceDeleteDialogComponent,
        AttendanceDeletePopupComponent
    ],
    entryComponents: [AttendanceComponent, AttendanceUpdateComponent, AttendanceDeleteDialogComponent, AttendanceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceModule {}
