import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveAttachment, LeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { LeaveAttachmentDeletePopupComponent } from 'app/entities/leave-attachment';
import { LeaveAttachmentExtendedService } from 'app/entities/leave-attachment-extended/leave-attachment-extended.service';
import { LeaveAttachmentExtendedComponent } from 'app/entities/leave-attachment-extended/leave-attachment-extended.component';
import { LeaveAttachmentDetailExtendedComponent } from 'app/entities/leave-attachment-extended/leave-attachment-detail-extended.component';
import { LeaveAttachmentUpdateExtendedComponent } from 'app/entities/leave-attachment-extended/leave-attachment-update-extended.component';

@Injectable({ providedIn: 'root' })
export class LeaveAttachmentExtendedResolve implements Resolve<ILeaveAttachment> {
    constructor(private service: LeaveAttachmentExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILeaveAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LeaveAttachment>) => response.ok),
                map((leaveAttachment: HttpResponse<LeaveAttachment>) => leaveAttachment.body)
            );
        }
        return of(new LeaveAttachment());
    }
}

export const leaveAttachmentExtendedRoute: Routes = [
    {
        path: '',
        component: LeaveAttachmentExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveAttachmentDetailExtendedComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveAttachmentUpdateExtendedComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveAttachmentUpdateExtendedComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveAttachmentPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: LeaveAttachmentDeletePopupComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
