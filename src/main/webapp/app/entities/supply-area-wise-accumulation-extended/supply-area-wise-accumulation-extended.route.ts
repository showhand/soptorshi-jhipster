import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyAreaWiseAccumulationExtendedService } from './supply-area-wise-accumulation-extended.service';
import { SupplyAreaWiseAccumulationExtendedComponent } from './supply-area-wise-accumulation-extended.component';
import { SupplyAreaWiseAccumulationDetailExtendedComponent } from './supply-area-wise-accumulation-detail-extended.component';
import { SupplyAreaWiseAccumulationUpdateExtendedComponent } from './supply-area-wise-accumulation-update-extended.component';
import { SupplyAreaWiseAccumulationDeletePopupExtendedComponent } from './supply-area-wise-accumulation-delete-dialog-extended.component';
import { SupplyAreaWiseAccumulationResolve } from 'app/entities/supply-area-wise-accumulation';

@Injectable({ providedIn: 'root' })
export class SupplyAreaWiseAccumulationExtendedResolve extends SupplyAreaWiseAccumulationResolve {
    constructor(service: SupplyAreaWiseAccumulationExtendedService) {
        super(service);
    }
}

export const supplyAreaWiseAccumulationExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyAreaWiseAccumulationExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyAreaWiseAccumulationDetailExtendedComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyAreaWiseAccumulationUpdateExtendedComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyAreaWiseAccumulationUpdateExtendedComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyAreaWiseAccumulationPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyAreaWiseAccumulationDeletePopupExtendedComponent,
        resolve: {
            supplyAreaWiseAccumulation: SupplyAreaWiseAccumulationExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyAreaWiseAccumulations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
