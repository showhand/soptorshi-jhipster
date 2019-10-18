import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Holiday } from 'app/shared/model/holiday.model';
import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayServiceExtended } from 'app/entities/holiday-extended/holiday.service.extended';
import { HolidayComponentExtended } from 'app/entities/holiday-extended/holiday.component.extended';
import { HolidayDetailComponentExtended } from 'app/entities/holiday-extended/holiday-detail.component.extended';
import { HolidayUpdateComponentExtended } from 'app/entities/holiday-extended/holiday-update.component.extended';
import { HolidayDeletePopupComponent } from 'app/entities/holiday';

@Injectable({ providedIn: 'root' })
export class HolidayResolveExtended implements Resolve<IHoliday> {
    constructor(private service: HolidayServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHoliday> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Holiday>) => response.ok),
                map((holiday: HttpResponse<Holiday>) => holiday.body)
            );
        }
        return of(new Holiday());
    }
}

export const holidayRouteExtended: Routes = [
    {
        path: '',
        component: HolidayComponentExtended,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER', 'ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayDetailComponentExtended,
        resolve: {
            holiday: HolidayResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HolidayUpdateComponentExtended,
        resolve: {
            holiday: HolidayResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HolidayUpdateComponentExtended,
        resolve: {
            holiday: HolidayResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidayPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: HolidayDeletePopupComponent,
        resolve: {
            holiday: HolidayResolveExtended
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
