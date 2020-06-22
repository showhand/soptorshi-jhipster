import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { SupplyOrderDetailsExtendedService } from './supply-order-details-extended.service';
import { SupplyOrderDetailsUpdateExtendedComponent } from './supply-order-details-update-extended.component';
import { SupplyOrderDetailsDeletePopupExtendedComponent } from './supply-order-details-delete-dialog-extended.component';
import { SupplyOrderDetailsResolve } from 'app/entities/supply-order-details';
import { SupplyOrderDetailsExtendedComponent } from 'app/entities/supply-order-details-extended/supply-order-details-extended.component';

@Injectable({ providedIn: 'root' })
export class SupplyOrderDetailsExtendedResolve extends SupplyOrderDetailsResolve {
    constructor(service: SupplyOrderDetailsExtendedService) {
        super(service);
    }
}

export const supplyOrderDetailsExtendedRoute: Routes = [
    {
        path: '',
        component: SupplyOrderDetailsExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_ZONE_MANAGER', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyOrderDetailsExtendedComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyOrderDetailsUpdateExtendedComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyOrderDetailsUpdateExtendedComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyOrderDetailsPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyOrderDetailsDeletePopupExtendedComponent,
        resolve: {
            supplyOrderDetails: SupplyOrderDetailsResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplyOrderDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
