import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapExtendedService } from './system-account-map-extended.service';
import { SystemAccountMapExtendedComponent } from './system-account-map-extended.component';
import { SystemAccountMapExtendedDetailComponent } from './system-account-map-extended-detail.component';
import { SystemAccountMapExtendedUpdateComponent } from './system-account-map-extended-update.component';
import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapDeletePopupComponent } from 'app/entities/system-account-map';

@Injectable({ providedIn: 'root' })
export class SystemAccountMapExtendedResolve implements Resolve<ISystemAccountMap> {
    constructor(private service: SystemAccountMapExtendedService) {}

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

export const systemAccountMapExtendedRoute: Routes = [
    {
        path: '',
        component: SystemAccountMapExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SystemAccountMapExtendedDetailComponent,
        resolve: {
            systemAccountMap: SystemAccountMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SystemAccountMapExtendedUpdateComponent,
        resolve: {
            systemAccountMap: SystemAccountMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SystemAccountMapExtendedUpdateComponent,
        resolve: {
            systemAccountMap: SystemAccountMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const systemAccountMapExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SystemAccountMapDeletePopupComponent,
        resolve: {
            systemAccountMap: SystemAccountMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemAccountMaps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
