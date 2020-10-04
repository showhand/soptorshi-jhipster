import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { StockInProcessExtendedService } from 'app/entities/stock-in-process-extended/stock-in-process-extended.service';
import { StockInProcessExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-extended.component';
import { StockInProcessDetailExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-detail-extended.component';
import { StockInProcessUpdateExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-update-extended.component';
import { StockInProcessDeletePopupExtendedComponent } from 'app/entities/stock-in-process-extended/stock-in-process-delete-dialog-extended.component';
import { StockInProcessResolve } from 'app/entities/stock-in-process';

@Injectable({ providedIn: 'root' })
export class StockInProcessExtendedResolve extends StockInProcessResolve {
    constructor(service: StockInProcessExtendedService) {
        super(service);
    }
}

export const stockInProcessExtendedRoute: Routes = [
    {
        path: '',
        component: StockInProcessExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInProcessDetailExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInProcessUpdateExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInProcessUpdateExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN', 'ROLE_INVENTORY_MANAGER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInProcessPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: StockInProcessDeletePopupExtendedComponent,
        resolve: {
            stockInProcess: StockInProcessExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INVENTORY_ADMIN'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
