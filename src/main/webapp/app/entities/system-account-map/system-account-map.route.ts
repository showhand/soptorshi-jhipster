import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapService } from './system-account-map.service';
import { SystemAccountMapComponent } from './system-account-map.component';
import { SystemAccountMapDetailComponent } from './system-account-map-detail.component';
import { SystemAccountMapUpdateComponent } from './system-account-map-update.component';
import { SystemAccountMapDeletePopupComponent } from './system-account-map-delete-dialog.component';
import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';

@Injectable({ providedIn: 'root' })
export class SystemAccountMapResolve implements Resolve<ISystemAccountMap> {
    constructor(private service: SystemAccountMapService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISystemAccountMap> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SystemAccountMap>) => response.ok),
                map((systemAccountMap: HttpResponse<SystemAccountMap>) => systemAccountMap.body)
            );
        }
        return of(new SystemAccountMap());
    }
}

export const systemAccountMapRoute: Routes = [
    {
        path: '',
        component: SystemAccountMapComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SystemAccountMapDetailComponent,
        resolve: {
            systemAccountMap: SystemAccountMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SystemAccountMapUpdateComponent,
        resolve: {
            systemAccountMap: SystemAccountMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SystemAccountMapUpdateComponent,
        resolve: {
            systemAccountMap: SystemAccountMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const systemAccountMapPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SystemAccountMapDeletePopupComponent,
        resolve: {
            systemAccountMap: SystemAccountMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
