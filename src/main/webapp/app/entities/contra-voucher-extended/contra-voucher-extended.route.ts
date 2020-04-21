import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map, switchMap } from 'rxjs/operators';
import { ContraVoucher } from 'app/shared/model/contra-voucher.model';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ContraVoucherExtendedService } from 'app/entities/contra-voucher-extended/contra-voucher-extended.service';
import {
    ContraVoucherComponent,
    ContraVoucherDeletePopupComponent,
    ContraVoucherDetailComponent,
    ContraVoucherUpdateComponent
} from 'app/entities/contra-voucher';
import { ContraVoucherExtendedComponent } from 'app/entities/contra-voucher-extended/contra-voucher-extended.component';
import { ContraVoucherExtendedUpdateComponent } from 'app/entities/contra-voucher-extended/contra-voucher-extended-update.component';
import { PaymentVoucher } from 'app/shared/model/payment-voucher.model';

@Injectable({ providedIn: 'root' })
export class ContraVoucherExtendedResolve implements Resolve<IContraVoucher> {
    constructor(private service: ContraVoucherExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContraVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        const applicationType = route.params['applicationType'] ? route.params['applicationType'] : null;
        const applicationId = route.params['applicationId'] ? route.params['applicationId'] : null;
        const voucherNo = route.params['voucherNo'] ? route.params['voucherNo'] : null;

        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContraVoucher>) => response.ok),
                map((contraVoucher: HttpResponse<ContraVoucher>) => contraVoucher.body)
            );
        } else if (applicationType && applicationId) {
            const contraVoucher = new ContraVoucher();
            contraVoucher.applicationId = applicationId;
            contraVoucher.applicationType = applicationType;
            return of(contraVoucher);
        } else if (voucherNo) {
            return this.service
                .query({
                    'voucherNo.equals': voucherNo
                })
                .pipe(switchMap(res => res.body));
        }
        return of(new ContraVoucher());
    }
}

export const contraVoucherExtendedRoute: Routes = [
    {
        path: '',
        component: ContraVoucherExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ContraVoucherDetailComponent,
        resolve: {
            contraVoucher: ContraVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ContraVoucherExtendedUpdateComponent,
        resolve: {
            contraVoucher: ContraVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ContraVoucherExtendedUpdateComponent,
        resolve: {
            contraVoucher: ContraVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'voucher-no/:voucherNo/edit',
        component: ContraVoucherExtendedUpdateComponent,
        resolve: {
            contraVoucher: ContraVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':applicationType/:applicationId/new',
        component: ContraVoucherExtendedUpdateComponent,
        resolve: {
            contraVoucher: ContraVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contraVoucherExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ContraVoucherDeletePopupComponent,
        resolve: {
            contraVoucher: ContraVoucherExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
