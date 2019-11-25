import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineExtendedService } from './special-allowance-time-line-extended.service';
import { SpecialAllowanceTimeLineExtendedComponent } from './special-allowance-time-line-extended.component';
import { SpecialAllowanceTimeLineExtendedDetailComponent } from './special-allowance-time-line-extended-detail.component';
import { SpecialAllowanceTimeLineExtendedUpdateComponent } from './special-allowance-time-line-extended-update.component';
import { SpecialAllowanceTimeLineExtendedDeletePopupComponent } from './special-allowance-time-line-extended-delete-dialog.component';
import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';

@Injectable({ providedIn: 'root' })
export class SpecialAllowanceTimeLineExtendedResolve implements Resolve<ISpecialAllowanceTimeLine> {
    constructor(private service: SpecialAllowanceTimeLineExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpecialAllowanceTimeLine> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SpecialAllowanceTimeLine>) => response.ok),
                map((specialAllowanceTimeLine: HttpResponse<SpecialAllowanceTimeLine>) => specialAllowanceTimeLine.body)
            );
        }
        return of(new SpecialAllowanceTimeLine());
    }
}

export const specialAllowanceTimeLineExtendedRoute: Routes = [
    {
        path: '',
        component: SpecialAllowanceTimeLineExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SpecialAllowanceTimeLineExtendedDetailComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SpecialAllowanceTimeLineExtendedUpdateComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SpecialAllowanceTimeLineExtendedUpdateComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const specialAllowanceTimeLineExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SpecialAllowanceTimeLineExtendedDeletePopupComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
