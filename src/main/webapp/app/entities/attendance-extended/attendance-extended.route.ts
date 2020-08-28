import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Attendance } from 'app/shared/model/attendance.model';
import { AttendanceExtendedService } from 'app/entities/attendance-extended/attendance-extended.service';
import { AttendanceExtendedComponent } from 'app/entities/attendance-extended/attendance-extended.component';
import { AttendanceDetailExtendedComponent } from 'app/entities/attendance-extended/attendance-detail-extended.component';
import { AttendanceUpdateExtendedComponent } from 'app/entities/attendance-extended/attendance-update-extended.component';
import { AttendanceDeletePopupExtendedComponent } from 'app/entities/attendance-extended/attendance-delete-dialog-extended.component';
import { MyAttendanceComponent } from 'app/entities/attendance-extended/my-attendance.component';
import { AttendanceResolve } from 'app/entities/attendance';

@Injectable({ providedIn: 'root' })
export class AttendanceExtendedResolve extends AttendanceResolve {
    constructor(service: AttendanceExtendedService) {
        super(service);
    }
}

export const attendanceExtendedRoute: Routes = [
    {
        path: '',
        component: AttendanceExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceDetailExtendedComponent,
        resolve: {
            attendance: AttendanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AttendanceUpdateExtendedComponent,
        resolve: {
            attendance: AttendanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Employee Attendance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-attendance',
        component: MyAttendanceComponent,
        resolve: {
            attendance: AttendanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER', 'ROLE_USER'],
            pageTitle: 'Attendance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AttendanceUpdateExtendedComponent,
        resolve: {
            attendance: AttendanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendancePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: AttendanceDeletePopupExtendedComponent,
        resolve: {
            attendance: AttendanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
