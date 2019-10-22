import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    AttendanceExcelUploadComponentExtended,
    AttendanceExcelUploadDetailComponentExtended,
    AttendanceExcelUploadUpdateComponentExtended,
    AttendanceExcelUploadDeletePopupComponentExtended,
    AttendanceExcelUploadDeleteDialogComponentExtended,
    attendanceExcelUploadRouteExtended,
    attendanceExcelUploadPopupRouteExtended
} from './';

const ENTITY_STATES = [...attendanceExcelUploadRouteExtended, ...attendanceExcelUploadPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AttendanceExcelUploadComponentExtended,
        AttendanceExcelUploadDetailComponentExtended,
        AttendanceExcelUploadUpdateComponentExtended,
        AttendanceExcelUploadDeleteDialogComponentExtended,
        AttendanceExcelUploadDeletePopupComponentExtended
    ],
    entryComponents: [
        AttendanceExcelUploadComponentExtended,
        AttendanceExcelUploadUpdateComponentExtended,
        AttendanceExcelUploadDeleteDialogComponentExtended,
        AttendanceExcelUploadDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiAttendanceExcelUploadModuleExtended {}
