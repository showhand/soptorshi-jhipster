import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { HolidayExtendedService } from 'app/entities/holiday-extended/holiday-extended.service';
import { HolidayExtendedComponent } from 'app/entities/holiday-extended/holiday-extended.component';
import { HolidayDetailExtendedComponent } from 'app/entities/holiday-extended/holiday-detail-extended.component';
import { HolidayUpdateExtendedComponent } from 'app/entities/holiday-extended/holiday-update-extended.component';
import { HolidayDeletePopupExtendedComponent } from 'app/entities/holiday-extended/holiday-delete-dialog-extended.component';
import { HolidayResolve } from 'app/entities/holiday';

@Injectable({ providedIn: 'root' })
export class HolidayExtendedResolve extends HolidayResolve {
    constructor(service: HolidayExtendedService) {
        super(service);
    }
}

export const holidayExtendedRoute: Routes = [
    {
        path: '',
        component: HolidayExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER', 'ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayDetailExtendedComponent,
        resolve: {
            holiday: HolidayExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HolidayUpdateExtendedComponent,
        resolve: {
            holiday: HolidayExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HolidayUpdateExtendedComponent,
        resolve: {
            holiday: HolidayExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidayPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: HolidayDeletePopupExtendedComponent,
        resolve: {
            holiday: HolidayExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
