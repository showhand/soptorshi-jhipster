import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountBalance, IAccountBalance } from 'app/shared/model/account-balance.model';
import {
    AccountBalanceComponent,
    AccountBalanceDeletePopupComponent,
    AccountBalanceDetailComponent,
    AccountBalanceUpdateComponent
} from 'app/entities/account-balance';
import { AccountBalanceExtendedService } from 'app/entities/account-balance-extended/account-balance-extended.service';
import { BalanceSheetComponent } from 'app/entities/account-balance-extended/balance-sheet.component';
import { ProfitAndLossComponent } from 'app/entities/account-balance-extended/profit-and-loss/profit-and-loss.component';
import { CashFlowComponent } from 'app/entities/account-balance-extended/cash-flow/cash-flow.component';

@Injectable({ providedIn: 'root' })
export class AccountBalanceExtendedResolve implements Resolve<IAccountBalance> {
    constructor(private service: AccountBalanceExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountBalance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AccountBalance>) => response.ok),
                map((accountBalance: HttpResponse<AccountBalance>) => accountBalance.body)
            );
        }
        return of(new AccountBalance());
    }
}

export const accountBalanceExtendedRoute: Routes = [
    {
        path: '',
        component: AccountBalanceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AccountBalanceDetailComponent,
        resolve: {
            accountBalance: AccountBalanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AccountBalanceUpdateComponent,
        resolve: {
            accountBalance: AccountBalanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'balance-sheet',
        component: BalanceSheetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'profit-and-loss',
        component: ProfitAndLossComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cash-flow',
        component: CashFlowComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AccountBalanceUpdateComponent,
        resolve: {
            accountBalance: AccountBalanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accountBalancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AccountBalanceDeletePopupComponent,
        resolve: {
            accountBalance: AccountBalanceExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AccountBalances'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
