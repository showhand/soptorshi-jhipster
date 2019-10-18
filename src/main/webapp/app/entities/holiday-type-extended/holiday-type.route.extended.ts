import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HolidayType } from 'app/shared/model/holiday-type.model';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeServiceExtended } from 'app/entities/holiday-type-extended/holiday-type.service.extended';
import { HolidayTypeComponentExtended } from 'app/entities/holiday-type-extended/holiday-type.component.extended';
import { HolidayTypeDetailComponentExtended } from 'app/entities/holiday-type-extended/holiday-type-detail.component.extended';
import { HolidayTypeUpdateComponentExtended } from 'app/entities/holiday-type-extended/holiday-type-update.component.extended';
import { HolidayTypeDeletePopupComponentExtended } from 'app/entities/holiday-type-extended/holiday-type-delete-dialog.component.extended';

@Injectable({ providedIn: 'root' })
export class HolidayTypeResolveExtended implements Resolve<IHolidayType> {
    constructor(private service: HolidayTypeServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHolidayType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HolidayType>) => response.ok),
                map((holidayType: HttpResponse<HolidayType>) => holidayType.body)
            );
        }
        return of(new HolidayType());
    }
}

export const holidayTypeRouteExtended: Routes = [
    {
        path: '',
        component: HolidayTypeComponentExtended,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayTypeDetailComponentExtended,
        resolve: {
            holidayType: HolidayTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HolidayTypeUpdateComponentExtended,
        resolve: {
            holidayType: HolidayTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HolidayTypeUpdateComponentExtended,
        resolve: {
            holidayType: HolidayTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidayTypePopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: HolidayTypeDeletePopupComponentExtended,
        resolve: {
            holidayType: HolidayTypeResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
