import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapService } from './system-group-map.service';
import { SystemGroupMapComponent } from './system-group-map.component';
import { SystemGroupMapDetailComponent } from './system-group-map-detail.component';
import { SystemGroupMapUpdateComponent } from './system-group-map-update.component';
import { SystemGroupMapDeletePopupComponent } from './system-group-map-delete-dialog.component';
import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';

@Injectable({ providedIn: 'root' })
export class SystemGroupMapResolve implements Resolve<ISystemGroupMap> {
    constructor(private service: SystemGroupMapService) {}

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

export const systemGroupMapRoute: Routes = [
    {
        path: '',
        component: SystemGroupMapComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SystemGroupMapDetailComponent,
        resolve: {
            systemGroupMap: SystemGroupMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SystemGroupMapUpdateComponent,
        resolve: {
            systemGroupMap: SystemGroupMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SystemGroupMapUpdateComponent,
        resolve: {
            systemGroupMap: SystemGroupMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const systemGroupMapPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SystemGroupMapDeletePopupComponent,
        resolve: {
            systemGroupMap: SystemGroupMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemGroupMaps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
