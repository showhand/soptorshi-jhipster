import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from './mst-account.service';
import { MstAccountComponent } from './mst-account.component';
import { MstAccountDetailComponent } from './mst-account-detail.component';
import { MstAccountUpdateComponent } from './mst-account-update.component';
import { MstAccountDeletePopupComponent } from './mst-account-delete-dialog.component';
import { IMstAccount } from 'app/shared/model/mst-account.model';

@Injectable({ providedIn: 'root' })
export class MstAccountResolve implements Resolve<IMstAccount> {
    constructor(private service: MstAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMstAccount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MstAccount>) => response.ok),
                map((mstAccount: HttpResponse<MstAccount>) => mstAccount.body)
            );
        }
        return of(new MstAccount());
    }
}

export const mstAccountRoute: Routes = [
    {
        path: '',
        component: MstAccountComponent,
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
        component: MstAccountDetailComponent,
        resolve: {
            mstAccount: MstAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MstAccountUpdateComponent,
        resolve: {
            mstAccount: MstAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MstAccountUpdateComponent,
        resolve: {
            mstAccount: MstAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mstAccountPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MstAccountDeletePopupComponent,
        resolve: {
            mstAccount: MstAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MstAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
