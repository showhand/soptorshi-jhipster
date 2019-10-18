import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Attendance } from 'app/shared/model/attendance.model';
import { AttendanceServiceExtended } from './attendance.service.extended';
import { AttendanceDeletePopupComponentExtended } from './attendance-delete-dialog.component.extended';
import { IAttendance } from 'app/shared/model/attendance.model';
import { MyAttendanceComponent } from 'app/entities/attendance/my-attendance.component';
import { AttendanceComponent, AttendanceResolve } from 'app/entities/attendance';
import { AttendanceComponentExtended } from 'app/entities/attendance-extended/attendance.component.extended';
import { AttendanceDetailComponentExtended } from 'app/entities/attendance-extended/attendance-detail.component.extended';
import { AttendanceUpdateComponentExtended } from 'app/entities/attendance-extended/attendance-update.component.extended';

@Injectable({ providedIn: 'root' })
export class AttendanceResolveExtended implements Resolve<IAttendance> {
    constructor(private service: AttendanceServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAttendance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Attendance>) => response.ok),
                map((attendance: HttpResponse<Attendance>) => attendance.body)
            );
        }
        return of(new Attendance());
    }
}

export const attendanceRouteExtended: Routes = [
    {
        path: '',
        component: AttendanceComponentExtended,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceDetailComponentExtended,
        resolve: {
            attendance: AttendanceResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AttendanceUpdateComponentExtended,
        resolve: {
            attendance: AttendanceResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Employee Attendance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'my-attendance',
        component: MyAttendanceComponent,
        resolve: {
            attendance: AttendanceResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER', 'ROLE_USER'],
            pageTitle: 'Attendance'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AttendanceUpdateComponentExtended,
        resolve: {
            attendance: AttendanceResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendancePopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: AttendanceDeletePopupComponentExtended,
        resolve: {
            attendance: AttendanceResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
