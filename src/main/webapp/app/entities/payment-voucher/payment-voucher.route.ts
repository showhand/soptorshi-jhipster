import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherService } from './payment-voucher.service';
import { PaymentVoucherComponent } from './payment-voucher.component';
import { PaymentVoucherDetailComponent } from './payment-voucher-detail.component';
import { PaymentVoucherUpdateComponent } from './payment-voucher-update.component';
import { PaymentVoucherDeletePopupComponent } from './payment-voucher-delete-dialog.component';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';

@Injectable({ providedIn: 'root' })
export class PaymentVoucherResolve implements Resolve<IPaymentVoucher> {
    constructor(private service: PaymentVoucherService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaymentVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PaymentVoucher>) => response.ok),
                map((paymentVoucher: HttpResponse<PaymentVoucher>) => paymentVoucher.body)
            );
        }
        return of(new PaymentVoucher());
    }
}

export const paymentVoucherRoute: Routes = [
    {
        path: '',
        component: PaymentVoucherComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PaymentVoucherDetailComponent,
        resolve: {
            paymentVoucher: PaymentVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PaymentVoucherUpdateComponent,
        resolve: {
            paymentVoucher: PaymentVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PaymentVoucherUpdateComponent,
        resolve: {
            paymentVoucher: PaymentVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentVoucherPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PaymentVoucherDeletePopupComponent,
        resolve: {
            paymentVoucher: PaymentVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
