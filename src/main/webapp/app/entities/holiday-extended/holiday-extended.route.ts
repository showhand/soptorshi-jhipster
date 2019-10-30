import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Holiday, IHoliday } from 'app/shared/model/holiday.model';
import { HolidayExtendedService } from 'app/entities/holiday-extended/holiday-extended.service';
import { HolidayExtendedComponent } from 'app/entities/holiday-extended/holiday-extended.component';
import { HolidayDetailExtendedComponent } from 'app/entities/holiday-extended/holiday-detail-extended.component';
import { HolidayUpdateExtendedComponent } from 'app/entities/holiday-extended/holiday-update-extended.component';
import { HolidayDeletePopupComponentExtended } from 'app/entities/holiday-extended/holiday-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class HolidayResolveExtended implements Resolve<IHoliday> {
    constructor(private service: HolidayExtendedService) {}

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

export const holidayExtendedRoute: Routes = [
    {
        path: '',
        component: HolidayExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER', 'ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayDetailExtendedComponent,
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
        component: HolidayUpdateExtendedComponent,
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
        component: HolidayUpdateExtendedComponent,
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
        component: HolidayDeletePopupComponentExtended,
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
