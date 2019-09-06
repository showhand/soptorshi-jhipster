import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from './mst-group.service';
import { MstGroupComponent } from './mst-group.component';
import { MstGroupDetailComponent } from './mst-group-detail.component';
import { MstGroupUpdateComponent } from './mst-group-update.component';
import { MstGroupDeletePopupComponent } from './mst-group-delete-dialog.component';
import { IMstGroup } from 'app/shared/model/mst-group.model';

@Injectable({ providedIn: 'root' })
export class MstGroupResolve implements Resolve<IMstGroup> {
    constructor(private service: MstGroupService) {}

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

export const mstGroupRoute: Routes = [
    {
        path: '',
        component: MstGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MstGroupDetailComponent,
        resolve: {
            mstGroup: MstGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MstGroupUpdateComponent,
        resolve: {
            mstGroup: MstGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MstGroupUpdateComponent,
        resolve: {
            mstGroup: MstGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mstGroupPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MstGroupDeletePopupComponent,
        resolve: {
            mstGroup: MstGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
