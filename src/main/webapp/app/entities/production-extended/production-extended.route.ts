import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { ProductionExtendedService } from './production-extended.service';
import { ProductionExtendedComponent } from './production-extended.component';
import { ProductionDetailExtendedComponent } from './production-detail-extended.component';
import { ProductionUpdateExtendedComponent } from './production-update-extended.component';
import { ProductionDeletePopupExtendedComponent } from './production-delete-dialog-extended.component';
import { ProductionResolve } from 'app/entities/production';

@Injectable({ providedIn: 'root' })
export class ProductionExtendedResolve extends ProductionResolve {
    constructor(service: ProductionExtendedService) {
        super(service);
    }
}

export const productionExtendedRoute: Routes = [
    {
        path: '',
        component: ProductionExtendedComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PRODUCTION_ADMIN', 'ROLE_PRODUCTION_MANAGER'],
            pageTitle: 'Productions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductionDetailExtendedComponent,
        resolve: {
            production: ProductionExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PRODUCTION_ADMIN', 'ROLE_PRODUCTION_MANAGER'],
            pageTitle: 'Productions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductionUpdateExtendedComponent,
        resolve: {
            production: ProductionExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PRODUCTION_ADMIN', 'ROLE_PRODUCTION_MANAGER'],
            pageTitle: 'Productions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductionUpdateExtendedComponent,
        resolve: {
            production: ProductionExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PRODUCTION_ADMIN', 'ROLE_PRODUCTION_MANAGER'],
            pageTitle: 'Productions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productionPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductionDeletePopupExtendedComponent,
        resolve: {
            production: ProductionExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_PRODUCTION_ADMIN'],
            pageTitle: 'Productions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
