import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceExcelUploadDeleteDialogExtendedComponent,
    AttendanceExcelUploadDeletePopupComponentExtended,
    AttendanceExcelUploadDetailExtendedComponent,
    AttendanceExcelUploadExtendedComponent,
    attendanceExcelUploadExtendedRoute,
    attendanceExcelUploadPopupRouteExtended,
    AttendanceExcelUploadUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...attendanceExcelUploadExtendedRoute, ...attendanceExcelUploadPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceExcelUploadExtendedComponent,
        AttendanceExcelUploadDetailExtendedComponent,
        AttendanceExcelUploadUpdateExtendedComponent,
        AttendanceExcelUploadDeleteDialogExtendedComponent,
        AttendanceExcelUploadDeletePopupComponentExtended
    ],
    entryComponents: [
        AttendanceExcelUploadExtendedComponent,
        AttendanceExcelUploadUpdateExtendedComponent,
        AttendanceExcelUploadDeleteDialogExtendedComponent,
        AttendanceExcelUploadDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceExcelUploadModuleExtended {}
