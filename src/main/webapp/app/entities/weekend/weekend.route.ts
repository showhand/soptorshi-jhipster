import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IWeekend, Weekend } from 'app/shared/model/weekend.model';
import { WeekendService } from './weekend.service';
import { WeekendComponent } from './weekend.component';
import { WeekendDetailComponent } from './weekend-detail.component';
import { WeekendUpdateComponent } from './weekend-update.component';
import { WeekendDeletePopupComponent } from './weekend-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class WeekendResolve implements Resolve<IWeekend> {
    constructor(private service: WeekendService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWeekend> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Weekend>) => response.ok),
                map((weekend: HttpResponse<Weekend>) => weekend.body)
            );
        }
        return of(new Weekend());
    }
}

export const weekendRoute: Routes = [
    {
        path: '',
        component: WeekendComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: WeekendDetailComponent,
        resolve: {
            weekend: WeekendResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: WeekendUpdateComponent,
        resolve: {
            weekend: WeekendResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: WeekendUpdateComponent,
        resolve: {
            weekend: WeekendResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const weekendPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: WeekendDeletePopupComponent,
        resolve: {
            weekend: WeekendResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Weekends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
