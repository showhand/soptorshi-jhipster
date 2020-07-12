import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyZoneExtendedService } from './supply-zone-extended.service';
import { SupplyZoneExtendedComponent } from './supply-zone-extended.component';
import { SupplyZoneDetailExtendedComponent } from './supply-zone-detail-extended.component';
import { SupplyZoneUpdateExtendedComponent } from './supply-zone-update-extended.component';
import { SupplyZoneDeletePopupExtendedComponent } from './supply-zone-delete-dialog-extended.component';
import { SupplyZoneResolve } from 'app/entities/supply-zone';

@Injectable({ providedIn: 'root' })
export class SupplyZoneExtendedResolve extends SupplyZoneResolve {
    constructor(service: SupplyZoneExtendedService) {
        super(service);
    }
}

export const supplyZoneExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyZoneExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyZoneDetailExtendedComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyZoneUpdateExtendedComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyZoneUpdateExtendedComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyZonePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyZoneDeletePopupExtendedComponent,
        resolve: {
            supplyZone: SupplyZoneResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyZones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
