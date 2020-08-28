import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AttendanceExcelUploadExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-extended.component';
import { AttendanceExcelUploadDetailExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-detail-extended.component';
import { AttendanceExcelUploadUpdateExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-update-extended.component';
import { AttendanceExcelUploadDeletePopupExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-delete-dialog-extended.component';
import { AttendanceExcelUploadExtendedService } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-extended.service';
import { AttendanceExcelUploadResolve } from 'app/entities/attendance-excel-upload';

@Injectable({ providedIn: 'root' })
export class AttendanceExcelUploadExtendedResolve extends AttendanceExcelUploadResolve {
    constructor(service: AttendanceExcelUploadExtendedService) {
        super(service);
    }
}

export const attendanceExcelUploadExtendedRoute: Routes = [
    {
        path: '',
        component: AttendanceExcelUploadExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceExcelUploadDetailExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AttendanceExcelUploadUpdateExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AttendanceExcelUploadUpdateExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendanceExcelUploadPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: AttendanceExcelUploadDeletePopupExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
