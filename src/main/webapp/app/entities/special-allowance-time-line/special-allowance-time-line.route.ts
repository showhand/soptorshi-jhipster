import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineService } from './special-allowance-time-line.service';
import { SpecialAllowanceTimeLineComponent } from './special-allowance-time-line.component';
import { SpecialAllowanceTimeLineDetailComponent } from './special-allowance-time-line-detail.component';
import { SpecialAllowanceTimeLineUpdateComponent } from './special-allowance-time-line-update.component';
import { SpecialAllowanceTimeLineDeletePopupComponent } from './special-allowance-time-line-delete-dialog.component';
import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';

@Injectable({ providedIn: 'root' })
export class SpecialAllowanceTimeLineResolve implements Resolve<ISpecialAllowanceTimeLine> {
    constructor(private service: SpecialAllowanceTimeLineService) {}

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

export const specialAllowanceTimeLineRoute: Routes = [
    {
        path: '',
        component: SpecialAllowanceTimeLineComponent,
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
        component: SpecialAllowanceTimeLineDetailComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SpecialAllowanceTimeLineUpdateComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SpecialAllowanceTimeLineUpdateComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const specialAllowanceTimeLinePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SpecialAllowanceTimeLineDeletePopupComponent,
        resolve: {
            specialAllowanceTimeLine: SpecialAllowanceTimeLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpecialAllowanceTimeLines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
