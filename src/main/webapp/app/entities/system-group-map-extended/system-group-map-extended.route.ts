import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapExtendedService } from './system-group-map-extended.service';
import { SystemGroupMapExtendedComponent } from './system-group-map-extended.component';
import { SystemGroupMapExtendedDetailComponent } from './system-group-map-extended-detail.component';
import { SystemGroupMapExtendedUpdateComponent } from './system-group-map-extended-update.component';
import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapDeletePopupComponent } from 'app/entities/system-group-map';

@Injectable({ providedIn: 'root' })
export class SystemGroupMapExtendedResolve implements Resolve<ISystemGroupMap> {
    constructor(private service: SystemGroupMapExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISystemGroupMap> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SystemGroupMap>) => response.ok),
                map((systemGroupMap: HttpResponse<SystemGroupMap>) => systemGroupMap.body)
            );
        }
        return of(new SystemGroupMap());
    }
}

export const systemGroupMapExtendedRoute: Routes = [
    {
        path: '',
        component: SystemGroupMapExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SystemGroupMapExtendedDetailComponent,
        resolve: {
            systemGroupMap: SystemGroupMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SystemGroupMapExtendedUpdateComponent,
        resolve: {
            systemGroupMap: SystemGroupMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SystemGroupMapExtendedUpdateComponent,
        resolve: {
            systemGroupMap: SystemGroupMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const systemGroupMapExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SystemGroupMapDeletePopupComponent,
        resolve: {
            systemGroupMap: SystemGroupMapExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
