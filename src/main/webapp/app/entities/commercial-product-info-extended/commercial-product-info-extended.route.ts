import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CommercialProductInfoExtendedService } from './commercial-product-info-extended.service';
import { CommercialProductInfoExtendedComponent } from './commercial-product-info-extended.component';
import { CommercialProductInfoDetailExtendedComponent } from './commercial-product-info-detail-extended.component';
import { CommercialProductInfoUpdateExtendedComponent } from './commercial-product-info-update-extended.component';
import { CommercialProductInfoDeletePopupExtendedComponent } from './commercial-product-info-delete-dialog-extended.component';
import { CommercialProductInfoResolve } from 'app/entities/commercial-product-info';

@Injectable({ providedIn: 'root' })
export class CommercialProductInfoExtendedResolve extends CommercialProductInfoResolve {
    constructor(service: CommercialProductInfoExtendedService) {
        super(service);
    }
}

export const commercialProductInfoExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialProductInfoExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialProductInfoDetailExtendedComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialProductInfoUpdateExtendedComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialProductInfoUpdateExtendedComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialProductInfoPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialProductInfoDeletePopupExtendedComponent,
        resolve: {
            commercialProductInfo: CommercialProductInfoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProductInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
