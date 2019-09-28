import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearExtendedService } from './financial-account-year-extended.service';
import { FinancialAccountYearExtendedComponent } from './financial-account-year-extended.component';
import { FinancialAccountYearExtendedDetailComponent } from './financial-account-year-extended-detail.component';
import { FinancialAccountYearExtendedUpdateComponent } from './financial-account-year-extended-update.component';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearDeletePopupComponent } from 'app/entities/financial-account-year';

@Injectable({ providedIn: 'root' })
export class FinancialAccountYearExtendedResolve implements Resolve<IFinancialAccountYear> {
    constructor(private service: FinancialAccountYearExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFinancialAccountYear> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FinancialAccountYear>) => response.ok),
                map((financialAccountYear: HttpResponse<FinancialAccountYear>) => financialAccountYear.body)
            );
        }
        return of(new FinancialAccountYear());
    }
}

export const financialAccountYearExtendedRoute: Routes = [
    {
        path: '',
        component: FinancialAccountYearExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,desc',
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FinancialAccountYearExtendedDetailComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FinancialAccountYearExtendedUpdateComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FinancialAccountYearExtendedUpdateComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const financialAccountYearExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FinancialAccountYearDeletePopupComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
