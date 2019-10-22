import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BalanceType, IMstAccount, MstAccount, ReservedFlag } from 'app/shared/model/mst-account.model';
import { MstAccountDeletePopupComponent, MstAccountService } from 'app/entities/mst-account';
import { MstAccountExtendedComponent } from 'app/entities/mst-account-extended/mst-account-extended.component';
import { MstAccountExtendedDetailComponent } from 'app/entities/mst-account-extended/mst-account-extended-detail.component';
import { MstAccountExtendedUpdateComponent } from 'app/entities/mst-account-extended/mst-account-extended-update.component';

@Injectable({ providedIn: 'root' })
export class MstAccountExtendedResolve implements Resolve<IMstAccount> {
    constructor(private service: MstAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMstAccount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MstAccount>) => response.ok),
                map((mstAccount: HttpResponse<MstAccount>) => mstAccount.body)
            );
        } else {
            let account = new MstAccount();
            account.yearOpenBalance = 0;
            account.yearOpenBalanceType = BalanceType.DEBIT;
            account.reservedFlag = ReservedFlag.NOT_RESERVED;
            return of(account);
        }
        return of(new MstAccount());
    }
}

export const mstAccountExtendedRoute: Routes = [
    {
        path: '',
        component: MstAccountExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MstAccountExtendedDetailComponent,
        resolve: {
            mstAccount: MstAccountExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MstAccountExtendedUpdateComponent,
        resolve: {
            mstAccount: MstAccountExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MstAccountExtendedUpdateComponent,
        resolve: {
            mstAccount: MstAccountExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mstAccountExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MstAccountDeletePopupComponent,
        resolve: {
            mstAccount: MstAccountExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
