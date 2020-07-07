import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyAreaManagerExtendedService } from './supply-area-manager-extended.service';
import { SupplyAreaManagerExtendedComponent } from './supply-area-manager-extended.component';
import { SupplyAreaManagerDetailExtendedComponent } from './supply-area-manager-detail-extended.component';
import { SupplyAreaManagerUpdateExtendedComponent } from './supply-area-manager-update-extended.component';
import { SupplyAreaManagerDeletePopupExtendedComponent } from './supply-area-manager-delete-dialog-extended.component';
import { SupplyAreaManagerResolve } from 'app/entities/supply-area-manager';

@Injectable({ providedIn: 'root' })
export class SupplyAreaManagerExtendedResolve extends SupplyAreaManagerResolve {
    constructor(service: SupplyAreaManagerExtendedService) {
        super(service);
    }
}

export const supplyAreaManagerExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyAreaManagerExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyAreaManagerDetailExtendedComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyAreaManagerUpdateExtendedComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyAreaManagerUpdateExtendedComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyAreaManagerPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyAreaManagerDeletePopupExtendedComponent,
        resolve: {
            supplyAreaManager: SupplyAreaManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyAreaManagers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
