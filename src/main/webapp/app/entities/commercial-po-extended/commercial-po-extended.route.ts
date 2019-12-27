import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CommercialPoExtendedService } from './commercial-po-extended.service';
import { CommercialPoExtendedComponent } from './commercial-po-extended.component';
import { CommercialPoDetailExtendedComponent } from './commercial-po-detail-extended.component';
import { CommercialPoUpdateExtendedComponent } from './commercial-po-update-extended.component';
import { CommercialPoResolve } from 'app/entities/commercial-po';
import { CommercialPoDeletePopupExtendedComponent } from 'app/entities/commercial-po-extended/commercial-po-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialPoExtendedResolve extends CommercialPoResolve {
    constructor(service: CommercialPoExtendedService) {
        super(service);
    }
}

export const commercialPoExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPoExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPoDetailExtendedComponent,
        resolve: {
            commercialPo: CommercialPoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPoUpdateExtendedComponent,
        resolve: {
            commercialPo: CommercialPoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPoUpdateExtendedComponent,
        resolve: {
            commercialPo: CommercialPoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPoPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPoDeletePopupExtendedComponent,
        resolve: {
            commercialPo: CommercialPoExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
