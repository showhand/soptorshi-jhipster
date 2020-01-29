import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherDeletePopupComponent, PaymentVoucherService } from 'app/entities/payment-voucher';
import { PaymentVoucherExtendedComponent } from 'app/entities/payment-voucher-extended/payment-voucher-extended.component';
import { PaymentVoucherExtendedDetailComponent } from 'app/entities/payment-voucher-extended/payment-voucher-extended-detail.component';
import { PaymentVoucherExtendedUpdateComponent } from 'app/entities/payment-voucher-extended/payment-voucher-extended-update.component';
import { JournalVoucher } from 'app/shared/model/journal-voucher.model';

@Injectable({ providedIn: 'root' })
export class PaymentVoucherExtendedResolve implements Resolve<IPaymentVoucher> {
    constructor(private service: PaymentVoucherService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaymentVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        const applicationType = route.params['applicationType'] ? route.params['applicationType'] : null;
        const applicationId = route.params['applicationId'] ? route.params['applicationId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PaymentVoucher>) => response.ok),
                map((paymentVoucher: HttpResponse<PaymentVoucher>) => paymentVoucher.body)
            );
        } else if (applicationType && applicationId) {
            const paymentVoucher = new PaymentVoucher();
            paymentVoucher.applicationId = applicationId;
            paymentVoucher.applicationType = applicationType;
            return of(paymentVoucher);
        } else {
            return of(new PaymentVoucher());
        }
    }
}

export const paymentVoucherExtendedRoute: Routes = [
    {
        path: '',
        component: PaymentVoucherExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,desc',
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PaymentVoucherExtendedDetailComponent,
        resolve: {
            paymentVoucher: PaymentVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PaymentVoucherExtendedUpdateComponent,
        resolve: {
            paymentVoucher: PaymentVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PaymentVoucherExtendedUpdateComponent,
        resolve: {
            paymentVoucher: PaymentVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':applicationType/:applicationId/edit',
        component: PaymentVoucherExtendedUpdateComponent,
        resolve: {
            paymentVoucher: PaymentVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentVoucherExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PaymentVoucherDeletePopupComponent,
        resolve: {
            paymentVoucher: PaymentVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
