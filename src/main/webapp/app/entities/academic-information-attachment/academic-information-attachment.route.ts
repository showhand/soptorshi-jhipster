import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';
import { AcademicInformationAttachmentService } from './academic-information-attachment.service';
import { AcademicInformationAttachmentComponent } from './academic-information-attachment.component';
import { AcademicInformationAttachmentDetailComponent } from './academic-information-attachment-detail.component';
import { AcademicInformationAttachmentUpdateComponent } from './academic-information-attachment-update.component';
import { AcademicInformationAttachmentDeletePopupComponent } from './academic-information-attachment-delete-dialog.component';
import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

@Injectable({ providedIn: 'root' })
export class AcademicInformationAttachmentResolve implements Resolve<IAcademicInformationAttachment> {
    constructor(private service: AcademicInformationAttachmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAcademicInformationAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;

        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AcademicInformationAttachment>) => response.ok),
                map((academicInformationAttachment: HttpResponse<AcademicInformationAttachment>) => academicInformationAttachment.body)
            );
        } else if (employeeId) {
            const academicInformationAttachment: IAcademicInformationAttachment = new AcademicInformationAttachment();
            academicInformationAttachment.employeeId = employeeId;
            return of(academicInformationAttachment);
        }
        return of(new AcademicInformationAttachment());
    }
}

export const academicInformationAttachmentRoute: Routes = [
    {
        path: '',
        component: AcademicInformationAttachmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AcademicInformationAttachmentDetailComponent,
        resolve: {
            academicInformationAttachment: AcademicInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AcademicInformationAttachmentUpdateComponent,
        resolve: {
            academicInformationAttachment: AcademicInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AcademicInformationAttachmentUpdateComponent,
        resolve: {
            academicInformationAttachment: AcademicInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new-for-employee',
        component: AcademicInformationAttachmentUpdateComponent,
        resolve: {
            academicInformationAttachment: AcademicInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'AcademicInformationAttachments',
            breadcrumb: 'New Academic Information Attachment for Employee'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const academicInformationAttachmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AcademicInformationAttachmentDeletePopupComponent,
        resolve: {
            academicInformationAttachment: AcademicInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademicInformationAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
