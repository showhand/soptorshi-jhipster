import { Routes } from '@angular/router';
import {
    PurchaseCommitteeDeletePopupComponent,
    PurchaseCommitteeDetailComponent,
    PurchaseCommitteeResolve,
    PurchaseCommitteeUpdateComponent
} from 'app/entities/purchase-committee';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { PurchaseCommitteeExtendedComponent } from 'app/entities/purchase-committee-extended/purchase-committee-extended.component';

export const purchaseCommitteeExtendedRoute: Routes = [
    {
        path: '',
        component: PurchaseCommitteeExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PurchaseCommitteeDetailComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PurchaseCommitteeUpdateComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PurchaseCommitteeUpdateComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseCommitteeExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PurchaseCommitteeDeletePopupComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
