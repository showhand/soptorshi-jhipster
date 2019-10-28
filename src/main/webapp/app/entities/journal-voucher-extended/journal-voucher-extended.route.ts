import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherExtendedService } from './journal-voucher-extended.service';
import { JournalVoucherExtendedComponent } from './journal-voucher-extended.component';
import { JournalVoucherExtendedDetailComponent } from './journal-voucher-extended-detail.component';
import { JournalVoucherExtendedUpdateComponent } from './journal-voucher-extended-update.component';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherDeletePopupComponent } from 'app/entities/journal-voucher';
import { DtTransaction, IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';
import { JournalVoucherTransactionUpdateComponent } from 'app/entities/journal-voucher-extended/journal-voucher-transaction-update.component';

@Injectable({ providedIn: 'root' })
export class JournalVoucherExtendedResolve implements Resolve<IJournalVoucher> {
    constructor(private service: JournalVoucherExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJournalVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        const currencyId = route.params['currencyId'] ? route.params['currencyId'] : null;
        const balanceType = route.params['balanceType'] ? route.params['balanceType'] : null;
        const conversionFactor = route.params['conversionFactor'] ? route.params['conversionFactor'] : null;

        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JournalVoucher>) => response.ok),
                map((journalVoucher: HttpResponse<JournalVoucher>) => journalVoucher.body)
            );
        }
        return of(new JournalVoucher());
    }
}

@Injectable({ providedIn: 'root' })
export class JournalVoucherTransactionResolve implements Resolve<IDtTransaction> {
    constructor(private service: DtTransactionExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJournalVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        const currencyId = route.params['currencyId'] ? route.params['currencyId'] : null;
        const balanceType = route.params['balanceType'] ? route.params['balanceType'] : null;
        const conversionFactor = route.params['conversionFactorValue'] ? route.params['conversionFactorValue'] : null;

        if (currencyId && balanceType && conversionFactor) {
            let dtTransaction = new DtTransaction();
            dtTransaction.currencyId = currencyId;
            dtTransaction.balanceType = balanceType;
            dtTransaction.convFactor = conversionFactor;
            return of(dtTransaction);
        }
        return of(new DtTransaction());
    }
}

export const journalVoucherExtendedRoute: Routes = [
    {
        path: '',
        component: JournalVoucherExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JournalVoucherExtendedDetailComponent,
        resolve: {
            journalVoucher: JournalVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JournalVoucherExtendedUpdateComponent,
        resolve: {
            journalVoucher: JournalVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JournalVoucherExtendedUpdateComponent,
        resolve: {
            journalVoucher: JournalVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':currencyId/:balanceType/:conversionFactorValue/new-transaction',
        component: JournalVoucherTransactionUpdateComponent,
        resolve: {
            dtTransaction: JournalVoucherTransactionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Journal Voucher Transaction'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const journalVoucherExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JournalVoucherDeletePopupComponent,
        resolve: {
            journalVoucher: JournalVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JournalVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];