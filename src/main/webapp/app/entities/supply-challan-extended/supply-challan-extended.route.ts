import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyChallanExtendedService } from './supply-challan-extended.service';
import { SupplyChallanExtendedComponent } from './supply-challan-extended.component';
import { SupplyChallanDetailExtendedComponent } from './supply-challan-detail-extended.component';
import { SupplyChallanUpdateExtendedComponent } from './supply-challan-update-extended.component';
import { SupplyChallanDeletePopupExtendedComponent } from './supply-challan-delete-dialog-extended.component';
import { SupplyChallanResolve } from 'app/entities/supply-challan';

@Injectable({ providedIn: 'root' })
export class SupplyChallanExtendedResolve extends SupplyChallanResolve {
    constructor(service: SupplyChallanExtendedService) {
        super(service);
    }
}

export const supplyChallanExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyChallanExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyChallanDetailExtendedComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyChallanUpdateExtendedComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyChallanUpdateExtendedComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyChallanPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyChallanDeletePopupExtendedComponent,
        resolve: {
            supplyChallan: SupplyChallanResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplyChallans'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
