import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HolidayType, IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeExtendedService } from 'app/entities/holiday-type-extended/holiday-type-extended.service';
import { HolidayTypeExtendedComponent } from 'app/entities/holiday-type-extended/holiday-type-extended.component';
import { HolidayTypeDetailExtendedComponent } from 'app/entities/holiday-type-extended/holiday-type-detail-extended.component';
import { HolidayTypeUpdateExtendedComponent } from 'app/entities/holiday-type-extended/holiday-type-update-extended.component';
import { HolidayTypeDeletePopupComponentExtended } from 'app/entities/holiday-type-extended/holiday-type-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class HolidayTypeResolveExtended implements Resolve<IHolidayType> {
    constructor(private service: HolidayTypeExtendedService) {}

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

export const holidayTypeExtendedRoute: Routes = [
    {
        path: '',
        component: HolidayTypeExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HOLIDAY_MANAGER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayTypeDetailExtendedComponent,
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
        component: HolidayTypeUpdateExtendedComponent,
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
        component: HolidayTypeUpdateExtendedComponent,
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
