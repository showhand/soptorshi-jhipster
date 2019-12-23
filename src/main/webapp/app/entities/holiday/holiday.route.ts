import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Holiday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';
import { HolidayComponent } from './holiday.component';
import { HolidayDetailComponent } from './holiday-detail.component';
import { HolidayUpdateComponent } from './holiday-update.component';
import { HolidayDeletePopupComponent } from './holiday-delete-dialog.component';
import { IHoliday } from 'app/shared/model/holiday.model';

@Injectable({ providedIn: 'root' })
export class HolidayResolve implements Resolve<IHoliday> {
    constructor(private service: HolidayService) {}

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

export const holidayRoute: Routes = [
    {
        path: '',
        component: HolidayComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayDetailComponent,
        resolve: {
            holiday: HolidayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HolidayUpdateComponent,
        resolve: {
            holiday: HolidayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HolidayUpdateComponent,
        resolve: {
            holiday: HolidayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidayPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HolidayDeletePopupComponent,
        resolve: {
            holiday: HolidayResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Holidays'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
