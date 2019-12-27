import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CommercialBudgetExtendedService } from './commercial-budget-extended.service';
import { CommercialBudgetExtendedComponent } from './commercial-budget-extended.component';
import { CommercialBudgetDetailExtendedComponent } from './commercial-budget-detail-extended.component';
import { CommercialBudgetUpdateExtendedComponent } from './commercial-budget-update-extended.component';
import { CommercialBudgetDeletePopupExtendedComponent } from './commercial-budget-delete-dialog-extended.component';
import { CommercialBudgetResolve } from 'app/entities/commercial-budget';

@Injectable({ providedIn: 'root' })
export class CommercialBudgetExtendedResolve extends CommercialBudgetResolve {
    constructor(service: CommercialBudgetExtendedService) {
        super(service);
    }
}

export const commercialBudgetExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialBudgetExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialBudgetDetailExtendedComponent,
        resolve: {
            commercialBudget: CommercialBudgetExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialBudgetUpdateExtendedComponent,
        resolve: {
            commercialBudget: CommercialBudgetExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialBudgetUpdateExtendedComponent,
        resolve: {
            commercialBudget: CommercialBudgetExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialBudgetPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialBudgetDeletePopupExtendedComponent,
        resolve: {
            commercialBudget: CommercialBudgetExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
