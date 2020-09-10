import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveApplicationExtendedComponent } from './leave-application-extended.component';
import { LeaveApplicationDetailExtendedComponent } from './leave-application-detail-extended.component';
import { LeaveApplicationUpdateExtendedComponent } from './leave-application-update-extended.component';
import { LeaveApplicationDeletePopupExtendedComponent } from './leave-application-delete-dialog-extended.component';
import { LeaveApplicationResolve } from 'app/entities/leave-application';
import { OthersLeaveApplicationComponent } from 'app/entities/leave-application-extended/others-leave-application.component';
import { OthersLeaveApplicationHistoryComponent } from 'app/entities/leave-application-extended/others-leave-application-history.component';
import { ReviewLeaveApplicationComponent } from 'app/entities/leave-application-extended/review-leave-application.component';

@Injectable({ providedIn: 'root' })
export class LeaveApplicationExtendedResolve extends LeaveApplicationResolve {
    constructor(service: LeaveApplicationExtendedService) {
        super(service);
    }
}

export const leaveApplicationExtendedRoute: Routes = [
    {
        path: '',
        component: LeaveApplicationExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveApplicationDetailExtendedComponent,
        resolve: {
            leaveApplication: LeaveApplicationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveApplicationUpdateExtendedComponent,
        resolve: {
            leaveApplication: LeaveApplicationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER', 'ROLE_USER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new/others',
        component: OthersLeaveApplicationComponent,
        resolve: {
            leaveApplication: LeaveApplicationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'history/others',
        component: OthersLeaveApplicationHistoryComponent,
        resolve: {
            leaveApplication: LeaveApplicationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'review',
        component: ReviewLeaveApplicationComponent,
        resolve: {
            leaveApplication: LeaveApplicationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveApplicationUpdateExtendedComponent,
        resolve: {
            leaveApplication: LeaveApplicationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN', 'ROLE_LEAVE_MANAGER'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveApplicationPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: LeaveApplicationDeletePopupExtendedComponent,
        resolve: {
            leaveApplication: LeaveApplicationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveApplications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
