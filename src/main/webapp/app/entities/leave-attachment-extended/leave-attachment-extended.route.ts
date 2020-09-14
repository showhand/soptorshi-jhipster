import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { LeaveAttachmentResolve } from 'app/entities/leave-attachment';
import { LeaveAttachmentExtendedService } from 'app/entities/leave-attachment-extended/leave-attachment-extended.service';
import { LeaveAttachmentExtendedComponent } from 'app/entities/leave-attachment-extended/leave-attachment-extended.component';
import { LeaveAttachmentDetailExtendedComponent } from 'app/entities/leave-attachment-extended/leave-attachment-detail-extended.component';
import { LeaveAttachmentUpdateExtendedComponent } from 'app/entities/leave-attachment-extended/leave-attachment-update-extended.component';
import { LeaveAttachmentDeletePopupExtendedComponent } from 'app/entities/leave-attachment-extended/leave-attachment-delete-dialog-extended.component';
import { OthersLeaveAttachmentComponent } from 'app/entities/leave-attachment-extended/others-leave-attachment.component';
import { OthersLeaveAttachmentUpdateComponent } from 'app/entities/leave-attachment-extended/others-leave-attachment-update.component';

@Injectable({ providedIn: 'root' })
export class LeaveAttachmentExtendedResolve extends LeaveAttachmentResolve {
    constructor(service: LeaveAttachmentExtendedService) {
        super(service);
    }
}

export const leaveAttachmentExtendedRoute: Routes = [
    {
        path: '',
        component: LeaveAttachmentExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER', 'ROLE_USER'],
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
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER', 'ROLE_USER'],
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
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER', 'ROLE_USER'],
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
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'others',
        component: OthersLeaveAttachmentComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new/others',
        component: OthersLeaveAttachmentUpdateComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveAttachmentPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: LeaveAttachmentDeletePopupExtendedComponent,
        resolve: {
            leaveAttachment: LeaveAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
