import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { VoucherNumberControlService } from './voucher-number-control.service';
import { VoucherNumberControlComponent } from './voucher-number-control.component';
import { VoucherNumberControlDetailComponent } from './voucher-number-control-detail.component';
import { VoucherNumberControlUpdateComponent } from './voucher-number-control-update.component';
import { VoucherNumberControlDeletePopupComponent } from './voucher-number-control-delete-dialog.component';
import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';

@Injectable({ providedIn: 'root' })
export class VoucherNumberControlResolve implements Resolve<IVoucherNumberControl> {
    constructor(private service: VoucherNumberControlService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVoucherNumberControl> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VoucherNumberControl>) => response.ok),
                map((voucherNumberControl: HttpResponse<VoucherNumberControl>) => voucherNumberControl.body)
            );
        }
        return of(new VoucherNumberControl());
    }
}

export const voucherNumberControlRoute: Routes = [
    {
        path: '',
        component: VoucherNumberControlComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VoucherNumberControlDetailComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VoucherNumberControlUpdateComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VoucherNumberControlUpdateComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voucherNumberControlPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VoucherNumberControlDeletePopupComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
