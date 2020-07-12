import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { SupplySalesRepresentativeExtendedService } from './supply-sales-representative-extended.service';
import { SupplySalesRepresentativeExtendedComponent } from './supply-sales-representative-extended.component';
import { SupplySalesRepresentativeDetailExtendedComponent } from './supply-sales-representative-detail-extended.component';
import { SupplySalesRepresentativeUpdateExtendedComponent } from './supply-sales-representative-update-extended.component';
import { SupplySalesRepresentativeDeletePopupExtendedComponent } from './supply-sales-representative-delete-dialog-extended.component';
import { SupplySalesRepresentativeResolve } from 'app/entities/supply-sales-representative';

@Injectable({ providedIn: 'root' })
export class SupplySalesRepresentativeExtendedResolve extends SupplySalesRepresentativeResolve {
    constructor(service: SupplySalesRepresentativeExtendedService) {
        super(service);
    }
}

export const supplySalesRepresentativeExtendedRoute: Routes = [
    {
        path: '',
        component: SupplySalesRepresentativeExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplySalesRepresentativeDetailExtendedComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplySalesRepresentativeUpdateExtendedComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplySalesRepresentativeUpdateExtendedComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN', 'ROLE_SCM_AREA_MANAGER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplySalesRepresentativePopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplySalesRepresentativeDeletePopupExtendedComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SCM_ADMIN'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
