import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';
import { HolidayTypeComponent } from './holiday-type.component';
import { HolidayTypeDetailComponent } from './holiday-type-detail.component';
import { HolidayTypeUpdateComponent } from './holiday-type-update.component';
import { HolidayTypeDeletePopupComponent } from './holiday-type-delete-dialog.component';
import { IHolidayType } from 'app/shared/model/holiday-type.model';

@Injectable({ providedIn: 'root' })
export class HolidayTypeResolve implements Resolve<IHolidayType> {
    constructor(private service: HolidayTypeService) {}

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

export const holidayTypeRoute: Routes = [
    {
        path: '',
        component: HolidayTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HolidayTypeDetailComponent,
        resolve: {
            holidayType: HolidayTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HolidayTypeUpdateComponent,
        resolve: {
            holidayType: HolidayTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HolidayTypeUpdateComponent,
        resolve: {
            holidayType: HolidayTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const holidayTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HolidayTypeDeletePopupComponent,
        resolve: {
            holidayType: HolidayTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HolidayTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
