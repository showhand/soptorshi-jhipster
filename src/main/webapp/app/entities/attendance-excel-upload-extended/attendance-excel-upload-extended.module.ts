import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceExcelUploadDeleteDialogExtendedComponent,
    AttendanceExcelUploadDeletePopupExtendedComponent,
    AttendanceExcelUploadDetailExtendedComponent,
    AttendanceExcelUploadExtendedComponent,
    attendanceExcelUploadExtendedRoute,
    attendanceExcelUploadPopupExtendedRoute,
    AttendanceExcelUploadUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...attendanceExcelUploadExtendedRoute, ...attendanceExcelUploadPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceExcelUploadExtendedComponent,
        AttendanceExcelUploadDetailExtendedComponent,
        AttendanceExcelUploadUpdateExtendedComponent,
        AttendanceExcelUploadDeleteDialogExtendedComponent,
        AttendanceExcelUploadDeletePopupExtendedComponent
    ],
    entryComponents: [
        AttendanceExcelUploadExtendedComponent,
        AttendanceExcelUploadUpdateExtendedComponent,
        AttendanceExcelUploadDeleteDialogExtendedComponent,
        AttendanceExcelUploadDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceExcelUploadExtendedModule {}
