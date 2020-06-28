import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyZoneWiseAccumulation, SupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';
import { SupplyZoneWiseAccumulationService } from './supply-zone-wise-accumulation.service';
import { SupplyZoneWiseAccumulationComponent } from './supply-zone-wise-accumulation.component';
import { SupplyZoneWiseAccumulationDetailComponent } from './supply-zone-wise-accumulation-detail.component';
import { SupplyZoneWiseAccumulationUpdateComponent } from './supply-zone-wise-accumulation-update.component';
import { SupplyZoneWiseAccumulationDeletePopupComponent } from './supply-zone-wise-accumulation-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyZoneWiseAccumulationResolve implements Resolve<ISupplyZoneWiseAccumulation> {
    constructor(private service: SupplyZoneWiseAccumulationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyZoneWiseAccumulation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyZoneWiseAccumulation>) => response.ok),
                map((supplyZoneWiseAccumulation: HttpResponse<SupplyZoneWiseAccumulation>) => supplyZoneWiseAccumulation.body)
            );
        }
        return of(new SupplyZoneWiseAccumulation());
    }
}

export const supplyZoneWiseAccumulationRoute: Routes = [
    {
        path: '',
        component: SupplyZoneWiseAccumulationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyZoneWiseAccumulationDetailComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyZoneWiseAccumulationUpdateComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyZoneWiseAccumulationUpdateComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyZoneWiseAccumulationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyZoneWiseAccumulationDeletePopupComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
