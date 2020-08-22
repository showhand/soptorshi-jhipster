import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepreciationMap } from 'app/shared/model/depreciation-map.model';
import { DepreciationMapService } from './depreciation-map.service';
import { DepreciationMapComponent } from './depreciation-map.component';
import { DepreciationMapDetailComponent } from './depreciation-map-detail.component';
import { DepreciationMapUpdateComponent } from './depreciation-map-update.component';
import { DepreciationMapDeletePopupComponent } from './depreciation-map-delete-dialog.component';
import { IDepreciationMap } from 'app/shared/model/depreciation-map.model';

@Injectable({ providedIn: 'root' })
export class DepreciationMapResolve implements Resolve<IDepreciationMap> {
    constructor(private service: DepreciationMapService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepreciationMap> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DepreciationMap>) => response.ok),
                map((depreciationMap: HttpResponse<DepreciationMap>) => depreciationMap.body)
            );
        }
        return of(new DepreciationMap());
    }
}

export const depreciationMapRoute: Routes = [
    {
        path: '',
        component: DepreciationMapComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepreciationMapDetailComponent,
        resolve: {
            depreciationMap: DepreciationMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepreciationMapUpdateComponent,
        resolve: {
            depreciationMap: DepreciationMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationMaps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepreciationMapUpdateComponent,
        resolve: {
            depreciationMap: DepreciationMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationMaps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depreciationMapPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepreciationMapDeletePopupComponent,
        resolve: {
            depreciationMap: DepreciationMapResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationMaps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
