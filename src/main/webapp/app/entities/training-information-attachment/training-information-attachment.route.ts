import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';
import { TrainingInformationAttachmentService } from './training-information-attachment.service';
import { TrainingInformationAttachmentComponent } from './training-information-attachment.component';
import { TrainingInformationAttachmentDetailComponent } from './training-information-attachment-detail.component';
import { TrainingInformationAttachmentUpdateComponent } from './training-information-attachment-update.component';
import { TrainingInformationAttachmentDeletePopupComponent } from './training-information-attachment-delete-dialog.component';
import { ITrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';

@Injectable({ providedIn: 'root' })
export class TrainingInformationAttachmentResolve implements Resolve<ITrainingInformationAttachment> {
    constructor(private service: TrainingInformationAttachmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITrainingInformationAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;

        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TrainingInformationAttachment>) => response.ok),
                map((trainingInformationAttachment: HttpResponse<TrainingInformationAttachment>) => trainingInformationAttachment.body)
            );
        } else if (employeeId) {
            const trainingInformationAttachment: ITrainingInformationAttachment = new TrainingInformationAttachment();
            trainingInformationAttachment.employeeId = employeeId;
            return of(trainingInformationAttachment);
        }
        return of(new TrainingInformationAttachment());
    }
}

export const trainingInformationAttachmentRoute: Routes = [
    {
        path: '',
        component: TrainingInformationAttachmentComponent,
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TrainingInformationAttachmentDetailComponent,
        resolve: {
            trainingInformationAttachment: TrainingInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TrainingInformationAttachmentUpdateComponent,
        resolve: {
            trainingInformationAttachment: TrainingInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: TrainingInformationAttachmentUpdateComponent,
        resolve: {
            trainingInformationAttachment: TrainingInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TrainingInformationAttachmentUpdateComponent,
        resolve: {
            trainingInformationAttachment: TrainingInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformationAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trainingInformationAttachmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TrainingInformationAttachmentDeletePopupComponent,
        resolve: {
            trainingInformationAttachment: TrainingInformationAttachmentResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformationAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
