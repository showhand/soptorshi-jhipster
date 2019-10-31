import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReceiptVoucher } from 'app/shared/model/receipt-voucher.model';

import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { ReceiptVoucherDeletePopupComponent, ReceiptVoucherService } from 'app/entities/receipt-voucher';
import { ReceiptVoucherExtendedService } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended.service';
import { ReceiptVoucherExtendedDetailComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended-detail.component';
import { ReceiptVoucherExtendedUpdateComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended-update.component';
import { ReceiptVoucherExtendedComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended.component';

@Injectable({ providedIn: 'root' })
export class ReceiptVoucherExtendedResolve implements Resolve<IReceiptVoucher> {
    constructor(private service: ReceiptVoucherExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReceiptVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ReceiptVoucher>) => response.ok),
                map((receiptVoucher: HttpResponse<ReceiptVoucher>) => receiptVoucher.body)
            );
        }
        return of(new ReceiptVoucher());
    }
}

export const receiptVoucherExtendedRoute: Routes = [
    {
        path: '',
        component: ReceiptVoucherExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,desc',
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ReceiptVoucherExtendedDetailComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ReceiptVoucherExtendedUpdateComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ReceiptVoucherExtendedUpdateComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const receiptVoucherExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ReceiptVoucherDeletePopupComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
