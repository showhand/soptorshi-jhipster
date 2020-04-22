import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { OverTimeExtendedService } from './over-time-extended.service';
import { OverTimeExtendedComponent } from './over-time-extended.component';
import { OverTimeDetailExtendedComponent } from './over-time-detail-extended.component';
import { OverTimeUpdateExtendedComponent } from './over-time-update-extended.component';
import { OverTimeDeletePopupExtendedComponent } from './over-time-delete-dialog-extended.component';
import { OverTimeResolve } from 'app/entities/over-time';

@Injectable({ providedIn: 'root' })
export class OverTimeExtendedResolve extends OverTimeResolve {
    constructor(service: OverTimeExtendedService) {
        super(service);
    }
}

export const overTimeExtendedRoute: Routes = [
    {
        path: '',
        component: OverTimeExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OverTimes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OverTimeDetailExtendedComponent,
        resolve: {
            overTime: OverTimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OverTimes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OverTimeUpdateExtendedComponent,
        resolve: {
            overTime: OverTimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OverTimes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OverTimeUpdateExtendedComponent,
        resolve: {
            overTime: OverTimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OverTimes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const overTimePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: OverTimeDeletePopupExtendedComponent,
        resolve: {
            overTime: OverTimeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OverTimes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
