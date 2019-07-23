import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BudgetAllocation } from 'app/shared/model/budget-allocation.model';
import { BudgetAllocationService } from './budget-allocation.service';
import { BudgetAllocationComponent } from './budget-allocation.component';
import { BudgetAllocationDetailComponent } from './budget-allocation-detail.component';
import { BudgetAllocationUpdateComponent } from './budget-allocation-update.component';
import { BudgetAllocationDeletePopupComponent } from './budget-allocation-delete-dialog.component';
import { IBudgetAllocation } from 'app/shared/model/budget-allocation.model';

@Injectable({ providedIn: 'root' })
export class BudgetAllocationResolve implements Resolve<IBudgetAllocation> {
    constructor(public service: BudgetAllocationService) {}

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

export const budgetAllocationRoute: Routes = [
    {
        path: '',
        component: BudgetAllocationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },

    {
        path: ':id/view',
        component: BudgetAllocationDetailComponent,
        resolve: {
            budgetAllocation: BudgetAllocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':financialAccountYearId/new',
        component: BudgetAllocationUpdateComponent,
        resolve: {
            budgetAllocation: BudgetAllocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BudgetAllocationUpdateComponent,
        resolve: {
            budgetAllocation: BudgetAllocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BudgetAllocationUpdateComponent,
        resolve: {
            budgetAllocation: BudgetAllocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const budgetAllocationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BudgetAllocationDeletePopupComponent,
        resolve: {
            budgetAllocation: BudgetAllocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BudgetAllocations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
