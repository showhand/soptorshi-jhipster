import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { StockStatusExtendedService } from 'app/entities/stock-status-extended/stock-status-extended.service';
import { StockStatusExtendedComponent } from 'app/entities/stock-status-extended/stock-status-extended.component';
import { StockStatusDetailExtendedComponent } from 'app/entities/stock-status-extended/stock-status-detail-extended.component';
import { StockStatusUpdateExtendedComponent } from 'app/entities/stock-status-extended/stock-status-update-extended.component';
import { StockStatusDeletePopupExtendedComponent } from 'app/entities/stock-status-extended/stock-status-delete-dialog-extended.component';
import { StockStatusResolve } from 'app/entities/stock-status';

@Injectable({ providedIn: 'root' })
export class StockStatusExtendedResolve extends StockStatusResolve {
    constructor(service: StockStatusExtendedService) {
        super(service);
    }
}

export const stockStatusExtendedRoute: Routes = [
    {
        path: '',
        component: StockStatusExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockStatusDetailExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockStatusUpdateExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockStatusUpdateExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockStatusPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: StockStatusDeletePopupExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
