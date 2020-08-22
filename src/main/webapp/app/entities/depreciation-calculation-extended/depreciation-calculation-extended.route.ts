import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationExtendedService } from './depreciation-calculation-extended.service';
import { DepreciationCalculationExtendedComponent } from './depreciation-calculation-extended.component';
import { DepreciationCalculationExtendedDetailComponent } from './depreciation-calculation-extended-detail.component';
import { DepreciationCalculationExtendedUpdateComponent } from './depreciation-calculation-extended-update.component';
import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationExtendedDeletePopupComponent } from 'app/entities/depreciation-calculation-extended/depreciation-calculation-extended-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class DepreciationCalculationExtendedResolve implements Resolve<IDepreciationCalculation> {
    constructor(private service: DepreciationCalculationExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepreciationCalculation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const financialAccountYearId = route.params['financialAccountYearId'] ? route.params['financialAccountYearId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DepreciationCalculation>) => response.ok),
                map((depreciationCalculation: HttpResponse<DepreciationCalculation>) => depreciationCalculation.body)
            );
        }
        if (financialAccountYearId) {
            const depreciationCalculation = new DepreciationCalculation();
            depreciationCalculation.financialAccountYearId = financialAccountYearId;
            return of(depreciationCalculation);
        }
        return of(new DepreciationCalculation());
    }
}

export const depreciationCalculationExtendedRoute: Routes = [
    {
        path: '',
        component: DepreciationCalculationExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepreciationCalculationExtendedDetailComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepreciationCalculationExtendedUpdateComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':financialAccountYearId/new',
        component: DepreciationCalculationExtendedUpdateComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepreciationCalculationExtendedUpdateComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depreciationCalculationExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepreciationCalculationExtendedDeletePopupComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
