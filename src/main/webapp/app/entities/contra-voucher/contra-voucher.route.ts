import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ContraVoucherService } from './contra-voucher.service';
import { ContraVoucherComponent } from './contra-voucher.component';
import { ContraVoucherDetailComponent } from './contra-voucher-detail.component';
import { ContraVoucherUpdateComponent } from './contra-voucher-update.component';
import { ContraVoucherDeletePopupComponent } from './contra-voucher-delete-dialog.component';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';

@Injectable({ providedIn: 'root' })
export class ContraVoucherResolve implements Resolve<IContraVoucher> {
    constructor(private service: ContraVoucherService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContraVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ContraVoucher>) => response.ok),
                map((contraVoucher: HttpResponse<ContraVoucher>) => contraVoucher.body)
            );
        }
        return of(new ContraVoucher());
    }
}

export const contraVoucherRoute: Routes = [
    {
        path: '',
        component: ContraVoucherComponent,
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
            contraVoucher: ContraVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ContraVoucherUpdateComponent,
        resolve: {
            contraVoucher: ContraVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ContraVoucherUpdateComponent,
        resolve: {
            contraVoucher: ContraVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contraVoucherPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ContraVoucherDeletePopupComponent,
        resolve: {
            contraVoucher: ContraVoucherResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ContraVouchers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
