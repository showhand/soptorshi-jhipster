import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyAreaWiseAccumulation, SupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';
import { SupplyAreaWiseAccumulationService } from './supply-area-wise-accumulation.service';
import { SupplyAreaWiseAccumulationComponent } from './supply-area-wise-accumulation.component';
import { SupplyAreaWiseAccumulationDetailComponent } from './supply-area-wise-accumulation-detail.component';
import { SupplyAreaWiseAccumulationUpdateComponent } from './supply-area-wise-accumulation-update.component';
import { SupplyAreaWiseAccumulationDeletePopupComponent } from './supply-area-wise-accumulation-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyAreaWiseAccumulationResolve implements Resolve<ISupplyAreaWiseAccumulation> {
    constructor(private service: SupplyAreaWiseAccumulationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyAreaWiseAccumulation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyAreaWiseAccumulation>) => response.ok),
                map((supplyAreaWiseAccumulation: HttpResponse<SupplyAreaWiseAccumulation>) => supplyAreaWiseAccumulation.body)
            );
        }
        return of(new SupplyAreaWiseAccumulation());
    }
}

export const supplyAreaWiseAccumulationRoute: Routes = [
    {
        path: '',
        component: SupplyAreaWiseAccumulationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyAreaWiseAccumulationDetailComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyAreaWiseAccumulationUpdateComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyAreaWiseAccumulationUpdateComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyAreaWiseAccumulationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyAreaWiseAccumulationDeletePopupComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
