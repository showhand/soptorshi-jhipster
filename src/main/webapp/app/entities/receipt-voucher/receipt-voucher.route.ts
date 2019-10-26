import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { ReceiptVoucherService } from './receipt-voucher.service';
import { ReceiptVoucherComponent } from './receipt-voucher.component';
import { ReceiptVoucherDetailComponent } from './receipt-voucher-detail.component';
import { ReceiptVoucherUpdateComponent } from './receipt-voucher-update.component';
import { ReceiptVoucherDeletePopupComponent } from './receipt-voucher-delete-dialog.component';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';

@Injectable({ providedIn: 'root' })
export class ReceiptVoucherResolve implements Resolve<IReceiptVoucher> {
    constructor(private service: ReceiptVoucherService) {}

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

export const receiptVoucherRoute: Routes = [
    {
        path: '',
        component: ReceiptVoucherComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ReceiptVoucherDetailComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ReceiptVoucherUpdateComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ReceiptVoucherUpdateComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const receiptVoucherPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ReceiptVoucherDeletePopupComponent,
        resolve: {
            receiptVoucher: ReceiptVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReceiptVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
