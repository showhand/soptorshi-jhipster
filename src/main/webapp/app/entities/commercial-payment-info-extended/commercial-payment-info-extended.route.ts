import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CommercialPaymentInfoExtendedService } from './commercial-payment-info-extended.service';
import { CommercialPaymentInfoExtendedComponent } from './commercial-payment-info-extended.component';
import { CommercialPaymentInfoDetailExtendedComponent } from './commercial-payment-info-detail-extended.component';
import { CommercialPaymentInfoUpdateExtendedComponent } from './commercial-payment-info-update-extended.component';
import { CommercialPaymentInfoDeletePopupExtendedComponent } from './commercial-payment-info-delete-dialog-extended.component';
import { CommercialPaymentInfoResolve } from 'app/entities/commercial-payment-info';

@Injectable({ providedIn: 'root' })
export class CommercialPaymentInfoExtendedResolve extends CommercialPaymentInfoResolve {
    constructor(service: CommercialPaymentInfoExtendedService) {
        super(service);
    }
}

export const commercialPaymentInfoExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPaymentInfoExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPaymentInfoDetailExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPaymentInfoUpdateExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPaymentInfoUpdateExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPaymentInfoPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPaymentInfoDeletePopupExtendedComponent,
        resolve: {
            commercialPaymentInfo: CommercialPaymentInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPaymentInfos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
