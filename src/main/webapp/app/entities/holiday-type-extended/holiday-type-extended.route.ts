import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { HolidayTypeExtendedService } from 'app/entities/holiday-type-extended/holiday-type-extended.service';
import { HolidayTypeExtendedComponent } from 'app/entities/holiday-type-extended/holiday-type-extended.component';
import { HolidayTypeDetailExtendedComponent } from 'app/entities/holiday-type-extended/holiday-type-detail-extended.component';
import { HolidayTypeUpdateExtendedComponent } from 'app/entities/holiday-type-extended/holiday-type-update-extended.component';
import { HolidayTypeDeletePopupExtendedComponent } from 'app/entities/holiday-type-extended/holiday-type-delete-dialog-extended.component';
import { HolidayTypeResolve } from 'app/entities/holiday-type';

@Injectable({ providedIn: 'root' })
export class HolidayTypeExtendedResolve extends HolidayTypeResolve {
    constructor(service: HolidayTypeExtendedService) {
        super(service);
    }
}

export const holidayTypeExtendedRoute: Routes = [
    {
        path: '',
        component: HolidayTypeExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayTypeDetailExtendedComponent,
        resolve: {
            holidayType: HolidayTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HolidayTypeUpdateExtendedComponent,
        resolve: {
            holidayType: HolidayTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HolidayTypeUpdateExtendedComponent,
        resolve: {
            holidayType: HolidayTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidayTypePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: HolidayTypeDeletePopupExtendedComponent,
        resolve: {
            holidayType: HolidayTypeExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_ADMIN'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
