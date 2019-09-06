import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MonthlyBalance } from 'app/shared/model/monthly-balance.model';
import { MonthlyBalanceService } from './monthly-balance.service';
import { MonthlyBalanceComponent } from './monthly-balance.component';
import { MonthlyBalanceDetailComponent } from './monthly-balance-detail.component';
import { MonthlyBalanceUpdateComponent } from './monthly-balance-update.component';
import { MonthlyBalanceDeletePopupComponent } from './monthly-balance-delete-dialog.component';
import { IMonthlyBalance } from 'app/shared/model/monthly-balance.model';

@Injectable({ providedIn: 'root' })
export class MonthlyBalanceResolve implements Resolve<IMonthlyBalance> {
    constructor(private service: MonthlyBalanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMonthlyBalance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MonthlyBalance>) => response.ok),
                map((monthlyBalance: HttpResponse<MonthlyBalance>) => monthlyBalance.body)
            );
        }
        return of(new MonthlyBalance());
    }
}

export const monthlyBalanceRoute: Routes = [
    {
        path: '',
        component: MonthlyBalanceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MonthlyBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MonthlyBalanceDetailComponent,
        resolve: {
            monthlyBalance: MonthlyBalanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MonthlyBalanceUpdateComponent,
        resolve: {
            monthlyBalance: MonthlyBalanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MonthlyBalanceUpdateComponent,
        resolve: {
            monthlyBalance: MonthlyBalanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyBalances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const monthlyBalancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MonthlyBalanceDeletePopupComponent,
        resolve: {
            monthlyBalance: MonthlyBalanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MonthlyBalances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
