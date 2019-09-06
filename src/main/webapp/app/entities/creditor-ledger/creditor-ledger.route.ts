import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CreditorLedger } from 'app/shared/model/creditor-ledger.model';
import { CreditorLedgerService } from './creditor-ledger.service';
import { CreditorLedgerComponent } from './creditor-ledger.component';
import { CreditorLedgerDetailComponent } from './creditor-ledger-detail.component';
import { CreditorLedgerUpdateComponent } from './creditor-ledger-update.component';
import { CreditorLedgerDeletePopupComponent } from './creditor-ledger-delete-dialog.component';
import { ICreditorLedger } from 'app/shared/model/creditor-ledger.model';

@Injectable({ providedIn: 'root' })
export class CreditorLedgerResolve implements Resolve<ICreditorLedger> {
    constructor(private service: CreditorLedgerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICreditorLedger> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CreditorLedger>) => response.ok),
                map((creditorLedger: HttpResponse<CreditorLedger>) => creditorLedger.body)
            );
        }
        return of(new CreditorLedger());
    }
}

export const creditorLedgerRoute: Routes = [
    {
        path: '',
        component: CreditorLedgerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CreditorLedgers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CreditorLedgerDetailComponent,
        resolve: {
            creditorLedger: CreditorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditorLedgers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CreditorLedgerUpdateComponent,
        resolve: {
            creditorLedger: CreditorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditorLedgers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CreditorLedgerUpdateComponent,
        resolve: {
            creditorLedger: CreditorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditorLedgers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditorLedgerPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CreditorLedgerDeletePopupComponent,
        resolve: {
            creditorLedger: CreditorLedgerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditorLedgers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
