import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DebtorLedger } from 'app/shared/model/debtor-ledger.model';
import { DebtorLedgerService } from './debtor-ledger.service';
import { DebtorLedgerComponent } from './debtor-ledger.component';
import { DebtorLedgerDetailComponent } from './debtor-ledger-detail.component';
import { DebtorLedgerUpdateComponent } from './debtor-ledger-update.component';
import { DebtorLedgerDeletePopupComponent } from './debtor-ledger-delete-dialog.component';
import { IDebtorLedger } from 'app/shared/model/debtor-ledger.model';

@Injectable({ providedIn: 'root' })
export class DebtorLedgerResolve implements Resolve<IDebtorLedger> {
    constructor(private service: DebtorLedgerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDebtorLedger> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DebtorLedger>) => response.ok),
                map((debtorLedger: HttpResponse<DebtorLedger>) => debtorLedger.body)
            );
        }
        return of(new DebtorLedger());
    }
}

export const debtorLedgerRoute: Routes = [
    {
        path: '',
        component: DebtorLedgerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DebtorLedgers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DebtorLedgerDetailComponent,
        resolve: {
            debtorLedger: DebtorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DebtorLedgers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DebtorLedgerUpdateComponent,
        resolve: {
            debtorLedger: DebtorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DebtorLedgers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DebtorLedgerUpdateComponent,
        resolve: {
            debtorLedger: DebtorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DebtorLedgers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const debtorLedgerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DebtorLedgerDeletePopupComponent,
        resolve: {
            debtorLedger: DebtorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DebtorLedgers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
