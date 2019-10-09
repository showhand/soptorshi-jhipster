import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupExtendedService } from './mst-group-extended.service';
import { MstGroupExtendedComponent } from './mst-group-extended.component';
import { MstGroupExtendedDetailComponent } from './mst-group-extended-detail.component';
import { MstGroupExtendedUpdateComponent } from './mst-group-extended-update.component';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupDeletePopupComponent } from 'app/entities/mst-group';

@Injectable({ providedIn: 'root' })
export class MstGroupExtendedResolve implements Resolve<IMstGroup> {
    constructor(private service: MstGroupExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMstGroup> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MstGroup>) => response.ok),
                map((mstGroup: HttpResponse<MstGroup>) => mstGroup.body)
            );
        }
        return of(new MstGroup());
    }
}

export const mstGroupExtendedRoute: Routes = [
    {
        path: '',
        component: MstGroupExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MstGroupExtendedDetailComponent,
        resolve: {
            mstGroup: MstGroupExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MstGroupExtendedUpdateComponent,
        resolve: {
            mstGroup: MstGroupExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MstGroupExtendedUpdateComponent,
        resolve: {
            mstGroup: MstGroupExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mstGroupExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MstGroupDeletePopupComponent,
        resolve: {
            mstGroup: MstGroupExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
