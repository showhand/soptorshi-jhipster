import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AttendanceExcelUpload, IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-extended.component';
import { AttendanceExcelUploadDetailExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-detail-extended.component';
import { AttendanceExcelUploadUpdateExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-update-extended.component';
import { AttendanceExcelUploadDeletePopupExtendedComponent } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-delete-dialog-extended.component';
import { AttendanceExcelUploadExtendedService } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-extended.service';

@Injectable({ providedIn: 'root' })
export class AttendanceExcelUploadResolveExtended implements Resolve<IAttendanceExcelUpload> {
    constructor(private service: AttendanceExcelUploadExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAttendanceExcelUpload> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AttendanceExcelUpload>) => response.ok),
                map((attendanceExcelUpload: HttpResponse<AttendanceExcelUpload>) => attendanceExcelUpload.body)
            );
        }
        return of(new AttendanceExcelUpload());
    }
}

export const attendanceExcelUploadExtendedRoute: Routes = [
    {
        path: '',
        component: AttendanceExcelUploadExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceExcelUploadDetailExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AttendanceExcelUploadUpdateExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AttendanceExcelUploadUpdateExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendanceExcelUploadPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: AttendanceExcelUploadDeletePopupExtendedComponent,
        resolve: {
            attendanceExcelUpload: AttendanceExcelUploadResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
