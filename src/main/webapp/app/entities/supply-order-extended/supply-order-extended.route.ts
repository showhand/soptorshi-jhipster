import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyOrderExtendedService } from './supply-order-extended.service';
import { SupplyOrderExtendedComponent } from './supply-order-extended.component';
import { SupplyOrderDetailExtendedComponent } from './supply-order-detail-extended.component';
import { SupplyOrderUpdateExtendedComponent } from './supply-order-update-extended.component';
import { SupplyOrderDeletePopupExtendedComponent } from './supply-order-delete-dialog-extended.component';
import { SupplyOrderResolve } from 'app/entities/supply-order';

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
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
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
