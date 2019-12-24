import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialBudget, ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { CommercialBudgetService } from './commercial-budget.service';
import { CommercialBudgetComponent } from './commercial-budget.component';
import { CommercialBudgetDetailComponent } from './commercial-budget-detail.component';
import { CommercialBudgetUpdateComponent } from './commercial-budget-update.component';
import { CommercialBudgetDeletePopupComponent } from './commercial-budget-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialBudgetResolve implements Resolve<ICommercialBudget> {
    constructor(private service: CommercialBudgetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialBudget> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialBudget>) => response.ok),
                map((commercialBudget: HttpResponse<CommercialBudget>) => commercialBudget.body)
            );
        }
        return of(new CommercialBudget());
    }
}

export const commercialBudgetRoute: Routes = [
    {
        path: '',
        component: CommercialBudgetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialBudgetDetailComponent,
        resolve: {
            commercialBudget: CommercialBudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialBudgetUpdateComponent,
        resolve: {
            commercialBudget: CommercialBudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialBudgetUpdateComponent,
        resolve: {
            commercialBudget: CommercialBudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialBudgetPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialBudgetDeletePopupComponent,
        resolve: {
            commercialBudget: CommercialBudgetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialBudgets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
