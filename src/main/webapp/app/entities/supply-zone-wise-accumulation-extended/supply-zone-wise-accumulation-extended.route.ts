import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyZoneWiseAccumulationExtendedService } from './supply-zone-wise-accumulation-extended.service';
import { SupplyZoneWiseAccumulationExtendedComponent } from './supply-zone-wise-accumulation-extended.component';
import { SupplyZoneWiseAccumulationDetailExtendedComponent } from './supply-zone-wise-accumulation-detail-extended.component';
import { SupplyZoneWiseAccumulationUpdateExtendedComponent } from './supply-zone-wise-accumulation-update-extended.component';
import { SupplyZoneWiseAccumulationDeletePopupExtendedComponent } from './supply-zone-wise-accumulation-delete-dialog-extended.component';
import { SupplyZoneWiseAccumulationResolve } from 'app/entities/supply-zone-wise-accumulation';

@Injectable({ providedIn: 'root' })
export class SupplyZoneWiseAccumulationExtendedResolve extends SupplyZoneWiseAccumulationResolve {
    constructor(service: SupplyZoneWiseAccumulationExtendedService) {
        super(service);
    }
}

export const supplyZoneWiseAccumulationExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyZoneWiseAccumulationExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyZoneWiseAccumulationDetailExtendedComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyZoneWiseAccumulationUpdateExtendedComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyZoneWiseAccumulationUpdateExtendedComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyZoneWiseAccumulationPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyZoneWiseAccumulationDeletePopupExtendedComponent,
        resolve: {
            supplyZoneWiseAccumulation: SupplyZoneWiseAccumulationResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER'],
            pageTitle: 'SupplyZoneWiseAccumulations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
