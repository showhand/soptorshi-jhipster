import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { VoucherNumberControlExtendedService } from './voucher-number-control-extended.service';
import { VoucherNumberControlExtendedComponent } from './voucher-number-control-extended.component';
import { VoucherNumberControlExtendedDetailComponent } from './voucher-number-control-extended-detail.component';
import { VoucherNumberControlExtendedUpdateComponent } from './voucher-number-control-extended-update.component';
import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { VoucherNumberControlDeletePopupComponent } from 'app/entities/voucher-number-control';

@Injectable({ providedIn: 'root' })
export class VoucherNumberControlExtendedResolve implements Resolve<IVoucherNumberControl> {
    constructor(private service: VoucherNumberControlExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVoucherNumberControl> {
        const id = route.params['id'] ? route.params['id'] : null;
        const financialAccountYearId = route.params['financialAccountYearId'] ? route.params['financialAccountYearId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VoucherNumberControl>) => response.ok),
                map((voucherNumberControl: HttpResponse<VoucherNumberControl>) => voucherNumberControl.body)
            );
        } else if (financialAccountYearId) {
            const voucherNumberControl = new VoucherNumberControl();
            voucherNumberControl.financialAccountYearId = financialAccountYearId;
            return of(voucherNumberControl);
        }
        return of(new VoucherNumberControl());
    }
}

export const voucherNumberControlExtendedRoute: Routes = [
    {
        path: '',
        component: VoucherNumberControlExtendedComponent,
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
        component: VoucherNumberControlExtendedDetailComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VoucherNumberControlExtendedUpdateComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':financialAccountYearId/new',
        component: VoucherNumberControlExtendedUpdateComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VoucherNumberControlExtendedUpdateComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voucherNumberControlExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VoucherNumberControlDeletePopupComponent,
        resolve: {
            voucherNumberControl: VoucherNumberControlExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VoucherNumberControls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
