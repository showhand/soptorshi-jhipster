import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Advance } from 'app/shared/model/advance.model';
import { AdvanceService } from './advance.service';
import { AdvanceComponent } from './advance.component';
import { AdvanceDetailComponent } from './advance-detail.component';
import { AdvanceUpdateComponent } from './advance-update.component';
import { AdvanceDeletePopupComponent } from './advance-delete-dialog.component';
import { IAdvance } from 'app/shared/model/advance.model';

@Injectable({ providedIn: 'root' })
export class AdvanceResolve implements Resolve<IAdvance> {
    constructor(private service: AdvanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdvance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Advance>) => response.ok),
                map((advance: HttpResponse<Advance>) => advance.body)
            );
        }
        return of(new Advance());
    }
}

export const advanceRoute: Routes = [
    {
        path: '',
        component: AdvanceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Advances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdvanceDetailComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdvanceUpdateComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdvanceUpdateComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const advancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdvanceDeletePopupComponent,
        resolve: {
            advance: AdvanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
