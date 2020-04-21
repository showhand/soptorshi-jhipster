import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map, switchMap } from 'rxjs/operators';
import { JournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherExtendedService } from './journal-voucher-extended.service';
import { JournalVoucherExtendedComponent } from './journal-voucher-extended.component';
import { JournalVoucherExtendedDetailComponent } from './journal-voucher-extended-detail.component';
import { JournalVoucherExtendedUpdateComponent } from './journal-voucher-extended-update.component';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherDeletePopupComponent } from 'app/entities/journal-voucher';
import { DtTransaction, IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionExtendedResolve, DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';
import { JournalVoucherTransactionUpdateComponent } from 'app/entities/journal-voucher-extended/journal-voucher-transaction-update.component';
import { DtTransactionService } from 'app/entities/dt-transaction';

@Injectable({ providedIn: 'root' })
export class JournalVoucherExtendedResolve implements Resolve<IJournalVoucher> {
    constructor(private service: JournalVoucherExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJournalVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        const currencyId = route.params['currencyId'] ? route.params['currencyId'] : null;
        const balanceType = route.params['balanceType'] ? route.params['balanceType'] : null;
        const conversionFactor = route.params['conversionFactor'] ? route.params['conversionFactor'] : null;
        const voucherNo = route.params['voucherNo'] ? route.params['voucherNo'] : null;
        const applicationType = route.params['applicationType'] ? route.params['applicationType'] : null;
        const applicationId = route.params['applicationId'] ? route.params['applicationId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JournalVoucher>) => response.ok),
                map((journalVoucher: HttpResponse<JournalVoucher>) => journalVoucher.body)
            );
        } else if (applicationType && applicationId) {
            const journalVoucher = new JournalVoucher();
            journalVoucher.applicationId = applicationId;
            journalVoucher.applicationType = applicationType;
            return of(journalVoucher);
        } else if (voucherNo) {
            return this.service
                .query({
                    'voucherNo.equals': voucherNo
                })
                .pipe(switchMap(res => res.body));
        }
        return of(new JournalVoucher());
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
            defaultSort: 'id,desc',
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
        path: 'voucher-no/:voucherNo/edit',
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
        path: ':applicationType/:applicationId/new',
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
        path: ':journalVoucherId/new-transaction',
        component: JournalVoucherTransactionUpdateComponent,
        resolve: {
            dtTransaction: DtTransactionExtendedResolve
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
