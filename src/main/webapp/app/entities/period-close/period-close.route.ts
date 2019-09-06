import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PeriodClose } from 'app/shared/model/period-close.model';
import { PeriodCloseService } from './period-close.service';
import { PeriodCloseComponent } from './period-close.component';
import { PeriodCloseDetailComponent } from './period-close-detail.component';
import { PeriodCloseUpdateComponent } from './period-close-update.component';
import { PeriodCloseDeletePopupComponent } from './period-close-delete-dialog.component';
import { IPeriodClose } from 'app/shared/model/period-close.model';

@Injectable({ providedIn: 'root' })
export class PeriodCloseResolve implements Resolve<IPeriodClose> {
    constructor(private service: PeriodCloseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPeriodClose> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PeriodClose>) => response.ok),
                map((periodClose: HttpResponse<PeriodClose>) => periodClose.body)
            );
        }
        return of(new PeriodClose());
    }
}

export const periodCloseRoute: Routes = [
    {
        path: '',
        component: PeriodCloseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PeriodCloseDetailComponent,
        resolve: {
            periodClose: PeriodCloseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PeriodCloseUpdateComponent,
        resolve: {
            periodClose: PeriodCloseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PeriodCloseUpdateComponent,
        resolve: {
            periodClose: PeriodCloseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodClosePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PeriodCloseDeletePopupComponent,
        resolve: {
            periodClose: PeriodCloseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodCloses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
