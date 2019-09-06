import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionService } from './dt-transaction.service';
import { DtTransactionComponent } from './dt-transaction.component';
import { DtTransactionDetailComponent } from './dt-transaction-detail.component';
import { DtTransactionUpdateComponent } from './dt-transaction-update.component';
import { DtTransactionDeletePopupComponent } from './dt-transaction-delete-dialog.component';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';

@Injectable({ providedIn: 'root' })
export class DtTransactionResolve implements Resolve<IDtTransaction> {
    constructor(private service: DtTransactionService) {}

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

export const dtTransactionRoute: Routes = [
    {
        path: '',
        component: DtTransactionComponent,
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
        component: DtTransactionDetailComponent,
        resolve: {
            dtTransaction: DtTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DtTransactionUpdateComponent,
        resolve: {
            dtTransaction: DtTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DtTransactionUpdateComponent,
        resolve: {
            dtTransaction: DtTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dtTransactionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DtTransactionDeletePopupComponent,
        resolve: {
            dtTransaction: DtTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
