import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { LeaveTypeExtendedComponent } from 'app/entities/leave-type-extended/leave-type-extended.component';
import { LeaveTypeExtendedService } from 'app/entities/leave-type-extended/leave-type-extended.service';
import { LeaveTypeDetailExtendedComponent } from 'app/entities/leave-type-extended/leave-type-detail-extended.component';
import { LeaveTypeUpdateExtendedComponent } from 'app/entities/leave-type-extended/leave-type-update-extended.component';
import { LeaveTypeDeletePopupExtendedComponent } from 'app/entities/leave-type-extended/leave-type-delete-dialog-extended.component';
import { LeaveTypeResolve } from 'app/entities/leave-type';

@Injectable({ providedIn: 'root' })
export class LeaveTypeExtendedResolve extends LeaveTypeResolve {
    constructor(service: LeaveTypeExtendedService) {
        super(service);
    }
}

export const leaveTypeExtendedRoute: Routes = [
    {
        path: '',
        component: LeaveTypeExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LeaveTypeDetailExtendedComponent,
        resolve: {
            leaveType: LeaveTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LeaveTypeUpdateExtendedComponent,
        resolve: {
            leaveType: LeaveTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LeaveTypeUpdateExtendedComponent,
        resolve: {
            leaveType: LeaveTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaveTypePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: LeaveTypeDeletePopupExtendedComponent,
        resolve: {
            leaveType: LeaveTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_LEAVE_ADMIN'],
            pageTitle: 'LeaveTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
