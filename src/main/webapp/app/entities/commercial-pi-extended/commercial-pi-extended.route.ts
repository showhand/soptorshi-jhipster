import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CommercialPiExtendedService } from './commercial-pi-extended.service';
import { CommercialPiExtendedComponent } from './commercial-pi-extended.component';
import { CommercialPiDetailExtendedComponent } from './commercial-pi-detail-extended.component';
import { CommercialPiUpdateExtendedComponent } from './commercial-pi-update-extended.component';
import { CommercialPiDeletePopupExtendedComponent } from './commercial-pi-delete-dialog-extended.component';
import { CommercialPiResolve } from 'app/entities/commercial-pi';

@Injectable({ providedIn: 'root' })
export class CommercialPiExtendedResolve extends CommercialPiResolve {
    constructor(service: CommercialPiExtendedService) {
        super(service);
    }
}

export const commercialPiExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPiExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPiDetailExtendedComponent,
        resolve: {
            commercialPi: CommercialPiExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPiUpdateExtendedComponent,
        resolve: {
            commercialPi: CommercialPiExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPiUpdateExtendedComponent,
        resolve: {
            commercialPi: CommercialPiExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPiPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPiDeletePopupExtendedComponent,

        resolve: {
            commercialPi: CommercialPiExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPis'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
