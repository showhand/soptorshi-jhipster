import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyOrderExtendedService } from './supply-order-extended.service';
import { SupplyOrderExtendedComponent } from './supply-order-extended.component';
import { SupplyOrderDetailExtendedComponent } from './supply-order-detail-extended.component';
import { SupplyOrderUpdateExtendedComponent } from './supply-order-update-extended.component';
import { SupplyOrderDeletePopupExtendedComponent } from './supply-order-delete-dialog-extended.component';
import { SupplyOrderResolve } from 'app/entities/supply-order';
import { AccumulateOrderComponent } from 'app/entities/supply-order-extended/accumulate-order.component';

@Injectable({ providedIn: 'root' })
export class SupplyOrderExtendedResolve extends SupplyOrderResolve {
    constructor(service: SupplyOrderExtendedService) {
        super(service);
    }
}

export const supplyOrderExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyOrderExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'accumulate',
        component: AccumulateOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
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
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
