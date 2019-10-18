import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadServiceExtended } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload.service.extended';
import { AttendanceExcelUploadComponentExtended } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload.component.extended';
import { AttendanceExcelUploadDetailComponentExtended } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-detail.component.extended';
import { AttendanceExcelUploadUpdateComponentExtended } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-update.component.extended';
import { AttendanceExcelUploadDeletePopupComponentExtended } from 'app/entities/attendance-excel-upload-extended/attendance-excel-upload-delete-dialog.component.extended';

@Injectable({ providedIn: 'root' })
export class AttendanceExcelUploadResolveExtended implements Resolve<IAttendanceExcelUpload> {
    constructor(private service: AttendanceExcelUploadServiceExtended) {}

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

export const attendanceExcelUploadRouteExtended: Routes = [
    {
        path: '',
        component: AttendanceExcelUploadComponentExtended,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_ATTENDANCE_ADMIN'],
            pageTitle: 'AttendanceExcelUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AttendanceExcelUploadDetailComponentExtended,
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
        component: AttendanceExcelUploadUpdateComponentExtended,
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
        component: AttendanceExcelUploadUpdateComponentExtended,
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
        component: AttendanceExcelUploadDeletePopupComponentExtended,
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
