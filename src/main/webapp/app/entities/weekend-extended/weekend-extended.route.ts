import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { WeekendExtendedService } from './weekend-extended.service';
import { WeekendExtendedComponent } from './weekend-extended.component';
import { WeekendDetailExtendedComponent } from './weekend-detail-extended.component';
import { WeekendUpdateExtendedComponent } from './weekend-update-extended.component';
import { WeekendDeletePopupExtendedComponent } from './weekend-delete-dialog-extended.component';
import { WeekendResolve } from 'app/entities/weekend';

@Injectable({ providedIn: 'root' })
export class WeekendExtendedResolve extends WeekendResolve {
    constructor(service: WeekendExtendedService) {
        super(service);
    }
}

export const weekendExtendedRoute: Routes = [
    {
        path: '',
        component: WeekendExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_WEEKEND_ADMIN', 'ROLE_WEEKEND_MANAGER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: WeekendDetailExtendedComponent,
        resolve: {
            weekend: WeekendExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_WEEKEND_ADMIN', 'ROLE_WEEKEND_MANAGER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: WeekendUpdateExtendedComponent,
        resolve: {
            weekend: WeekendExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_WEEKEND_ADMIN', 'ROLE_WEEKEND_MANAGER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: WeekendUpdateExtendedComponent,
        resolve: {
            weekend: WeekendExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_WEEKEND_ADMIN', 'ROLE_WEEKEND_MANAGER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const weekendPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: WeekendDeletePopupExtendedComponent,
        resolve: {
            weekend: WeekendExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_WEEKEND_ADMIN'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
