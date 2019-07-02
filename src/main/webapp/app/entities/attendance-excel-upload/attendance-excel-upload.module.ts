import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceExcelUploadComponent,
    AttendanceExcelUploadDetailComponent,
    AttendanceExcelUploadUpdateComponent,
    AttendanceExcelUploadDeletePopupComponent,
    AttendanceExcelUploadDeleteDialogComponent,
    attendanceExcelUploadRoute,
    attendanceExcelUploadPopupRoute
} from './';

const ENTITY_STATES = [...attendanceExcelUploadRoute, ...attendanceExcelUploadPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceExcelUploadComponent,
        AttendanceExcelUploadDetailComponent,
        AttendanceExcelUploadUpdateComponent,
        AttendanceExcelUploadDeleteDialogComponent,
        AttendanceExcelUploadDeletePopupComponent
    ],
    entryComponents: [
        AttendanceExcelUploadComponent,
        AttendanceExcelUploadUpdateComponent,
        AttendanceExcelUploadDeleteDialogComponent,
        AttendanceExcelUploadDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceExcelUploadModule {}
