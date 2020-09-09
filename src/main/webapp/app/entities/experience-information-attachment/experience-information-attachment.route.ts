import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';
import { ExperienceInformationAttachmentService } from './experience-information-attachment.service';
import { ExperienceInformationAttachmentComponent } from './experience-information-attachment.component';
import { ExperienceInformationAttachmentDetailComponent } from './experience-information-attachment-detail.component';
import { ExperienceInformationAttachmentUpdateComponent } from './experience-information-attachment-update.component';
import { ExperienceInformationAttachmentDeletePopupComponent } from './experience-information-attachment-delete-dialog.component';
import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

@Injectable({ providedIn: 'root' })
export class ExperienceInformationAttachmentResolve implements Resolve<IExperienceInformationAttachment> {
    constructor(private service: ExperienceInformationAttachmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperienceInformationAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExperienceInformationAttachment>) => response.ok),
                map(
                    (experienceInformationAttachment: HttpResponse<ExperienceInformationAttachment>) => experienceInformationAttachment.body
                )
            );
        } else if (employeeId) {
            const experienceInformationAttachment: IExperienceInformationAttachment = new ExperienceInformationAttachment();
            experienceInformationAttachment.employeeId = employeeId;
            return of(experienceInformationAttachment);
        }
        return of(new ExperienceInformationAttachment());
    }
}

export const experienceInformationAttachmentRoute: Routes = [
    {
        path: '',
        component: ExperienceInformationAttachmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExperienceInformationAttachmentDetailComponent,
        resolve: {
            experienceInformationAttachment: ExperienceInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExperienceInformationAttachmentUpdateComponent,
        resolve: {
            experienceInformationAttachment: ExperienceInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExperienceInformationAttachmentUpdateComponent,
        resolve: {
            experienceInformationAttachment: ExperienceInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: ExperienceInformationAttachmentUpdateComponent,
        resolve: {
            experienceInformationAttachment: ExperienceInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'ExperienceInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experienceInformationAttachmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExperienceInformationAttachmentDeletePopupComponent,
        resolve: {
            experienceInformationAttachment: ExperienceInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ExperienceInformationAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
