import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyShopExtendedService } from './supply-shop-extended.service';
import { SupplyShopExtendedComponent } from './supply-shop-extended.component';
import { SupplyShopDetailExtendedComponent } from './supply-shop-detail-extended.component';
import { SupplyShopUpdateExtendedComponent } from './supply-shop-update-extended.component';
import { SupplyShopDeletePopupExtendedComponent } from './supply-shop-delete-dialog-extended.component';
import { SupplyShopResolve } from 'app/entities/supply-shop';

@Injectable({ providedIn: 'root' })
export class SupplyShopExtendedResolve extends SupplyShopResolve {
    constructor(service: SupplyShopExtendedService) {
        super(service);
    }
}

export const supplyShopExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyShopExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyShopDetailExtendedComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyShopUpdateExtendedComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyShopUpdateExtendedComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyShopPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyShopDeletePopupExtendedComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
