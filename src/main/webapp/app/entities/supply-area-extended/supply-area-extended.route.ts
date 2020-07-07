import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyAreaExtendedService } from './supply-area-extended.service';
import { SupplyAreaExtendedComponent } from './supply-area-extended.component';
import { SupplyAreaDetailExtendedComponent } from './supply-area-detail-extended.component';
import { SupplyAreaUpdateExtendedComponent } from './supply-area-update-extended.component';
import { SupplyAreaDeletePopupExtendedComponent } from './supply-area-delete-dialog-extended.component';
import { SupplyAreaResolve } from 'app/entities/supply-area';

@Injectable({ providedIn: 'root' })
export class SupplyAreaExtendedResolve extends SupplyAreaResolve {
    constructor(service: SupplyAreaExtendedService) {
        super(service);
    }
}

export const supplyAreaExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyAreaExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyAreaDetailExtendedComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyAreaUpdateExtendedComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyAreaUpdateExtendedComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyAreaPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyAreaDeletePopupExtendedComponent,
        resolve: {
            supplyArea: SupplyAreaResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyAreas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
