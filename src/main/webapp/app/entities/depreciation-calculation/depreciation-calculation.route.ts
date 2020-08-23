import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationService } from './depreciation-calculation.service';
import { DepreciationCalculationComponent } from './depreciation-calculation.component';
import { DepreciationCalculationDetailComponent } from './depreciation-calculation-detail.component';
import { DepreciationCalculationUpdateComponent } from './depreciation-calculation-update.component';
import { DepreciationCalculationDeletePopupComponent } from './depreciation-calculation-delete-dialog.component';
import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';

@Injectable({ providedIn: 'root' })
export class DepreciationCalculationResolve implements Resolve<IDepreciationCalculation> {
    constructor(private service: DepreciationCalculationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepreciationCalculation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DepreciationCalculation>) => response.ok),
                map((depreciationCalculation: HttpResponse<DepreciationCalculation>) => depreciationCalculation.body)
            );
        }
        return of(new DepreciationCalculation());
    }
}

export const depreciationCalculationRoute: Routes = [
    {
        path: '',
        component: DepreciationCalculationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepreciationCalculationDetailComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepreciationCalculationUpdateComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepreciationCalculationUpdateComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depreciationCalculationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepreciationCalculationDeletePopupComponent,
        resolve: {
            depreciationCalculation: DepreciationCalculationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DepreciationCalculations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
