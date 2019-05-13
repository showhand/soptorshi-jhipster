import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from './financial-account-year.service';
import { FinancialAccountYearComponent } from './financial-account-year.component';
import { FinancialAccountYearDetailComponent } from './financial-account-year-detail.component';
import { FinancialAccountYearUpdateComponent } from './financial-account-year-update.component';
import { FinancialAccountYearDeletePopupComponent } from './financial-account-year-delete-dialog.component';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';

@Injectable({ providedIn: 'root' })
export class FinancialAccountYearResolve implements Resolve<IFinancialAccountYear> {
    constructor(private service: FinancialAccountYearService) {}

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

export const financialAccountYearRoute: Routes = [
    {
        path: '',
        component: FinancialAccountYearComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FinancialAccountYearDetailComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FinancialAccountYearUpdateComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FinancialAccountYearUpdateComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const financialAccountYearPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FinancialAccountYearDeletePopupComponent,
        resolve: {
            financialAccountYear: FinancialAccountYearResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FinancialAccountYears'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
