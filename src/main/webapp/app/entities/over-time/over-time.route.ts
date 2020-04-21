import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IOverTime, OverTime } from 'app/shared/model/over-time.model';
import { OverTimeService } from './over-time.service';
import { OverTimeComponent } from './over-time.component';
import { OverTimeDetailComponent } from './over-time-detail.component';
import { OverTimeUpdateComponent } from './over-time-update.component';
import { OverTimeDeletePopupComponent } from './over-time-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class OverTimeResolve implements Resolve<IOverTime> {
    constructor(private service: OverTimeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOverTime> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OverTime>) => response.ok),
                map((overTime: HttpResponse<OverTime>) => overTime.body)
            );
        }
        return of(new OverTime());
    }
}

export const overTimeRoute: Routes = [
    {
        path: '',
        component: OverTimeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OverTimes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OverTimeDetailComponent,
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
        component: OverTimeUpdateComponent,
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
        component: OverTimeUpdateComponent,
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

export const overTimePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OverTimeDeletePopupComponent,
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
