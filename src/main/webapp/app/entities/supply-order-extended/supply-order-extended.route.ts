import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyOrderExtendedService } from './supply-order-extended.service';
import { SupplyOrderExtendedComponent } from './supply-order-extended.component';
import { SupplyOrderDetailExtendedComponent } from './supply-order-detail-extended.component';
import { SupplyOrderUpdateExtendedComponent } from './supply-order-update-extended.component';
import { SupplyOrderDeletePopupExtendedComponent } from './supply-order-delete-dialog-extended.component';
import { SupplyOrderResolve } from 'app/entities/supply-order';
import { AccumulateOrderComponent } from 'app/entities/supply-order-extended/accumulate-order.component';
import { Observable, of } from 'rxjs';
import { ISupplyOrder, SupplyOrder } from 'app/shared/model/supply-order.model';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class SupplyOrderExtendedResolve extends SupplyOrderResolve {
    supplyOrderExtendedService: SupplyOrderExtendedService;

    constructor(service: SupplyOrderExtendedService) {
        super(service);
        this.supplyOrderExtendedService = service;
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyOrder> {
        console.log('a vai ami eikhane');
        const id = route.params['id'] ? route.params['id'] : null;
        const orderId = route.params['orderId'] ? route.params['orderId'] : null;
        if (id) {
            return this.supplyOrderExtendedService.find(id).pipe(
                filter((response: HttpResponse<SupplyOrder>) => response.ok),
                map((supplyOrder: HttpResponse<SupplyOrder>) => supplyOrder.body)
            );
        } else if (orderId) {
            return this.supplyOrderExtendedService.find(orderId).pipe(
                filter((response: HttpResponse<SupplyOrder>) => response.ok),
                map((supplyOrder: HttpResponse<SupplyOrder>) => supplyOrder.body)
            );
        }
        return of(new SupplyOrder());
    }
}

export const supplyOrderExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyOrderExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyOrderDetailExtendedComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyOrderUpdateExtendedComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyOrderUpdateExtendedComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accumulate',
        component: AccumulateOrderComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'AccumulateSupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyOrderPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyOrderDeletePopupExtendedComponent,
        resolve: {
            supplyOrder: SupplyOrderResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
