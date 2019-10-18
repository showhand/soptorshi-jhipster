import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { LeaveAttachmentServiceExtended } from 'app/entities/leave-attachment-extended/leave-attachment.service.extended';
import { LeaveAttachmentComponentExtended } from 'app/entities/leave-attachment-extended/leave-attachment.component.extended';
import { LeaveAttachmentDetailComponentExtended } from 'app/entities/leave-attachment-extended/leave-attachment-detail.component.extended';
import { LeaveAttachmentUpdateComponentExtended } from 'app/entities/leave-attachment-extended/leave-attachment-update.component.extended';
import { LeaveAttachmentDeletePopupComponent } from 'app/entities/leave-attachment';

@Injectable({ providedIn: 'root' })
export class LeaveAttachmentResolveExtended implements Resolve<ILeaveAttachment> {
    constructor(private service: LeaveAttachmentServiceExtended) {}

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

export const leaveAttachmentRouteExtended: Routes = [
    {
        path: '',
        component: LeaveAttachmentComponentExtended,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveAttachmentDetailComponentExtended,
        resolve: {
            leaveAttachment: LeaveAttachmentResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveAttachmentUpdateComponentExtended,
        resolve: {
            leaveAttachment: LeaveAttachmentResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveAttachmentUpdateComponentExtended,
        resolve: {
            leaveAttachment: LeaveAttachmentResolveExtended
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
            leaveAttachment: LeaveAttachmentResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
