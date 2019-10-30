import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Attendance, IAttendance } from 'app/shared/model/attendance.model';
import { MyAttendanceComponent } from 'app/entities/attendance/my-attendance.component';
import { AttendanceExtendedService } from 'app/entities/attendance-extended/attendance-extended.service';
import { AttendanceExtendedComponent } from 'app/entities/attendance-extended/attendance-extended.component';
import { AttendanceDetailExtendedComponent } from 'app/entities/attendance-extended/attendance-detail-extended.component';
import { AttendanceUpdateExtendedComponent } from 'app/entities/attendance-extended/attendance-update-extended.component';
import { AttendanceDeletePopupComponentExtended } from 'app/entities/attendance-extended/attendance-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class AttendanceResolveExtended implements Resolve<IAttendance> {
    constructor(private service: AttendanceExtendedService) {}

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

export const attendanceExtendedRoute: Routes = [
    {
        path: '',
        component: AttendanceExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_MANAGER'],
            pageTitle: 'Attendances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceDetailExtendedComponent,
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
        component: AttendanceUpdateExtendedComponent,
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
        component: AttendanceUpdateExtendedComponent,
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
