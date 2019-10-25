import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionExtendedService } from './dt-transaction-extended.service';
import { DtTransactionExtendedComponent } from './dt-transaction-extended.component';
import { DtTransactionExtendedDetailComponent } from './dt-transaction-extended-detail.component';
import { DtTransactionExtendedUpdateComponent } from './dt-transaction-extended-update.component';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionDeletePopupComponent } from 'app/entities/dt-transaction';

@Injectable({ providedIn: 'root' })
export class DtTransactionExtendedResolve implements Resolve<IDtTransaction> {
    constructor(private service: DtTransactionExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDtTransaction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DtTransaction>) => response.ok),
                map((dtTransaction: HttpResponse<DtTransaction>) => dtTransaction.body)
            );
        }
        return of(new DtTransaction());
    }
}

export const dtTransactionExtendedRoute: Routes = [
    {
        path: '',
        component: DtTransactionExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DtTransactionExtendedDetailComponent,
        resolve: {
            dtTransaction: DtTransactionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DtTransactionExtendedUpdateComponent,
        resolve: {
            dtTransaction: DtTransactionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DtTransactionExtendedUpdateComponent,
        resolve: {
            dtTransaction: DtTransactionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dtTransactionExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DtTransactionDeletePopupComponent,
        resolve: {
            dtTransaction: DtTransactionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
