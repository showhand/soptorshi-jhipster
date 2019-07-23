import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import {
    BudgetAllocationComponent,
    BudgetAllocationDeletePopupComponent,
    BudgetAllocationDetailComponent,
    BudgetAllocationResolve,
    BudgetAllocationService,
    BudgetAllocationUpdateComponent
} from 'app/entities/budget-allocation';
import { UserRouteAccessService } from 'app/core';
import { Injectable } from '@angular/core';
import { BudgetAllocation, IBudgetAllocation } from 'app/shared/model/budget-allocation.model';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { BudgetAllocationExtendedComponent } from 'app/entities/budget-allocation-extended/budget-allocation-extended.component';
import { BudgetAllocationExtendedDetailComponent } from 'app/entities/budget-allocation-extended/budget-allocation-extended-detail.component';
import { BudgetAllocationExtendedUpdateExtendedComponent } from 'app/entities/budget-allocation-extended/budget-allocation-extended-update-extended.component';

@Injectable({ providedIn: 'root' })
export class BudgetAllocationExtendedResolve extends BudgetAllocationResolve {
    constructor(public service: BudgetAllocationService) {
        super(service);
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBudgetAllocation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const financialAccountYearId = route.params['financialAccountYearId'] ? route.params['financialAccountYearId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BudgetAllocation>) => response.ok),
                map((budgetAllocation: HttpResponse<BudgetAllocation>) => budgetAllocation.body)
            );
        } else if (financialAccountYearId) {
            const budgetAllocation: IBudgetAllocation = new BudgetAllocation();
            budgetAllocation.financialAccountYearId = financialAccountYearId;
            return of(budgetAllocation);
        }
        return of(new BudgetAllocation());
    }
}

export class BudgetParams {
    selectColumn: string;
    detailsColumn: string;
    showSelect: boolean;
    financialAccountYearId: number;

    constructor() {}
}

export const budgetAllocationExtendedRoute: Routes = [
    {
        path: '',
        component: BudgetAllocationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },

    {
        path: ':id/view',
        component: BudgetAllocationExtendedDetailComponent,
        resolve: {
            budgetAllocation: BudgetAllocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':financialAccountYearId/new',
        component: BudgetAllocationExtendedUpdateExtendedComponent,
        resolve: {
            budgetAllocation: BudgetAllocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BudgetAllocationExtendedUpdateExtendedComponent,
        resolve: {
            budgetAllocation: BudgetAllocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BudgetAllocationExtendedUpdateExtendedComponent,
        resolve: {
            budgetAllocation: BudgetAllocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const budgetAllocationExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BudgetAllocationDeletePopupComponent,
        resolve: {
            budgetAllocation: BudgetAllocationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
