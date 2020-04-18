import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyChallan, SupplyChallan } from 'app/shared/model/supply-challan.model';
import { SupplyChallanService } from './supply-challan.service';
import { SupplyChallanComponent } from './supply-challan.component';
import { SupplyChallanDetailComponent } from './supply-challan-detail.component';
import { SupplyChallanUpdateComponent } from './supply-challan-update.component';
import { SupplyChallanDeletePopupComponent } from './supply-challan-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyChallanResolve implements Resolve<ISupplyChallan> {
    constructor(private service: SupplyChallanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyChallan> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyChallan>) => response.ok),
                map((supplyChallan: HttpResponse<SupplyChallan>) => supplyChallan.body)
            );
        }
        return of(new SupplyChallan());
    }
}

export const supplyChallanRoute: Routes = [
    {
        path: '',
        component: SupplyChallanComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyChallanDetailComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyChallanUpdateComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyChallanUpdateComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyChallanPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyChallanDeletePopupComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
