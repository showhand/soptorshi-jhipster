import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DtTransaction, IDtTransaction, VoucherType } from 'app/shared/model/dt-transaction.model';
import { DtTransactionExtendedService } from './dt-transaction-extended.service';
import { DtTransactionExtendedDetailComponent } from './dt-transaction-extended-detail.component';
import { DtTransactionExtendedUpdateComponent } from './dt-transaction-extended-update.component';
import { DtTransactionDeletePopupComponent } from 'app/entities/dt-transaction';
import { JournalVoucherExtendedService } from 'app/entities/journal-voucher-extended';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { GeneralLedgerReportComponent } from 'app/entities/dt-transaction-extended/general-ledger-report.component';

@Injectable({ providedIn: 'root' })
export class DtTransactionExtendedResolve implements Resolve<IDtTransaction> {
    constructor(private service: DtTransactionExtendedService, private journalVoucherExtendedService: JournalVoucherExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDtTransaction> {
        const id = route.params['id'] ? route.params['id'] : null;
        const journalVoucherId = route.params['journalVoucherId'] ? route.params['journalVoucherId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DtTransaction>) => response.ok),
                map((dtTransaction: HttpResponse<DtTransaction>) => dtTransaction.body)
            );
        } else if (journalVoucherId) {
            return this.journalVoucherExtendedService.find(journalVoucherId).pipe(
                filter((response: HttpResponse<IJournalVoucher>) => response.ok),
                map((response: HttpResponse<IJournalVoucher>) => {
                    const journalVoucher = response.body;
                    const dtTransacton = new DtTransaction();
                    dtTransacton.convFactor = journalVoucher.conversionFactor;
                    dtTransacton.voucherNo = journalVoucher.voucherNo;
                    dtTransacton.voucherDate = journalVoucher.voucherDate;
                    dtTransacton.postDate = journalVoucher.postDate;
                    dtTransacton.type =
                        journalVoucher.type === null
                            ? null
                            : journalVoucher.type.toString() === VoucherType.BUYING
                            ? VoucherType.BUYING
                            : VoucherType.SELLING;
                    return dtTransacton;
                })
            );
        }
        return of(new DtTransaction());
    }
}

export const dtTransactionExtendedRoute: Routes = [
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
        path: 'general-ledger-report',
        component: GeneralLedgerReportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DtTransactions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':journalVoucherId/new',
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
