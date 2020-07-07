import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyZoneManagerExtendedService } from './supply-zone-manager-extended.service';
import { SupplyZoneManagerExtendedComponent } from './supply-zone-manager-extended.component';
import { SupplyZoneManagerDetailExtendedComponent } from './supply-zone-manager-detail-extended.component';
import { SupplyZoneManagerUpdateExtendedComponent } from './supply-zone-manager-update-extended.component';
import { SupplyZoneManagerDeletePopupExtendedComponent } from './supply-zone-manager-delete-dialog-extended.component';
import { SupplyZoneManagerResolve } from 'app/entities/supply-zone-manager';

@Injectable({ providedIn: 'root' })
export class SupplyZoneManagerExtendedResolve extends SupplyZoneManagerResolve {
    constructor(service: SupplyZoneManagerExtendedService) {
        super(service);
    }
}

export const supplyZoneManagerExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyZoneManagerExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyZoneManagerDetailExtendedComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyZoneManagerUpdateExtendedComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyZoneManagerUpdateExtendedComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyZoneManagerPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyZoneManagerDeletePopupExtendedComponent,
        resolve: {
            supplyZoneManager: SupplyZoneManagerResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZoneManagers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
