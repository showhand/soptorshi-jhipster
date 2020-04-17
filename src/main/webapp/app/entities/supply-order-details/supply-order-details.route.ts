import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyOrderDetails, SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { SupplyOrderDetailsService } from './supply-order-details.service';
import { SupplyOrderDetailsComponent } from './supply-order-details.component';
import { SupplyOrderDetailsDetailComponent } from './supply-order-details-detail.component';
import { SupplyOrderDetailsUpdateComponent } from './supply-order-details-update.component';
import { SupplyOrderDetailsDeletePopupComponent } from './supply-order-details-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyOrderDetailsResolve implements Resolve<ISupplyOrderDetails> {
    constructor(private service: SupplyOrderDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyOrderDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyOrderDetails>) => response.ok),
                map((supplyOrderDetails: HttpResponse<SupplyOrderDetails>) => supplyOrderDetails.body)
            );
        }
        return of(new SupplyOrderDetails());
    }
}

export const supplyOrderDetailsRoute: Routes = [
    {
        path: '',
        component: SupplyOrderDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyOrderDetailsDetailComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyOrderDetailsUpdateComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyOrderDetailsUpdateComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyOrderDetailsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyOrderDetailsDeletePopupComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
