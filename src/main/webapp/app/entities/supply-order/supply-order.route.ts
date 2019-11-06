import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyOrder, SupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from './supply-order.service';
import { SupplyOrderComponent } from './supply-order.component';
import { SupplyOrderDetailComponent } from './supply-order-detail.component';
import { SupplyOrderUpdateComponent } from './supply-order-update.component';
import { SupplyOrderDeletePopupComponent } from './supply-order-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyOrderResolve implements Resolve<ISupplyOrder> {
    constructor(private service: SupplyOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyOrder>) => response.ok),
                map((supplyOrder: HttpResponse<SupplyOrder>) => supplyOrder.body)
            );
        }
        return of(new SupplyOrder());
    }
}

export const supplyOrderRoute: Routes = [
    {
        path: '',
        component: SupplyOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyOrderDetailComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyOrderUpdateComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyOrderUpdateComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyOrderPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyOrderDeletePopupComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
