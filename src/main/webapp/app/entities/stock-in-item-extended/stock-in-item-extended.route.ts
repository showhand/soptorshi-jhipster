import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { StockInItemExtendedService } from 'app/entities/stock-in-item-extended/stock-in-item-extended.service';
import { StockInItemExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-extended.component';
import { StockInItemDetailExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-detail-extended.component';
import { StockInItemUpdateExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-update-extended.component';
import { StockInItemDeletePopupExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-delete-dialog-extended.component';
import { StockInItemResolve } from 'app/entities/stock-in-item';

@Injectable({ providedIn: 'root' })
export class StockInItemExtendedResolve extends StockInItemResolve {
    constructor(service: StockInItemExtendedService) {
        super(service);
    }
}

export const stockInItemExtendedRoute: Routes = [
    {
        path: '',
        component: StockInItemExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInItemDetailExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInItemUpdateExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInItemUpdateExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInItemPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: StockInItemDeletePopupExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
