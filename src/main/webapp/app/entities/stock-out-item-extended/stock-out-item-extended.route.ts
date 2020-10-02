import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { StockOutItemExtendedService } from 'app/entities/stock-out-item-extended/stock-out-item-extended.service';
import { StockOutItemExtendedComponent } from 'app/entities/stock-out-item-extended/stock-out-item-extended.component';
import { StockOutItemDetailExtendedComponent } from 'app/entities/stock-out-item-extended/stock-out-item-detail-extended.component';
import { StockOutItemUpdateExtendedComponent } from 'app/entities/stock-out-item-extended/stock-out-item-update-extended.component';
import { StockOutItemDeletePopupExtendedComponent } from 'app/entities/stock-out-item-extended/stock-out-item-delete-dialog-extended.component';
import { StockOutItemResolve } from 'app/entities/stock-out-item';

@Injectable({ providedIn: 'root' })
export class StockOutItemExtendedResolve extends StockOutItemResolve {
    constructor(service: StockOutItemExtendedService) {
        super(service);
    }
}

export const stockOutItemExtendedRoute: Routes = [
    {
        path: '',
        component: StockOutItemExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockOutItemDetailExtendedComponent,
        resolve: {
            stockOutItem: StockOutItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockOutItemUpdateExtendedComponent,
        resolve: {
            stockOutItem: StockOutItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockOutItemUpdateExtendedComponent,
        resolve: {
            stockOutItem: StockOutItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockOutItemPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: StockOutItemDeletePopupExtendedComponent,
        resolve: {
            stockOutItem: StockOutItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
