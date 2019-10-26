import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherExtendedService } from './journal-voucher-extended.service';
import { JournalVoucherExtendedComponent } from './journal-voucher-extended.component';
import { JournalVoucherExtendedDetailComponent } from './journal-voucher-extended-detail.component';
import { JournalVoucherExtendedUpdateComponent } from './journal-voucher-extended-update.component';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherDeletePopupComponent } from 'app/entities/journal-voucher';

@Injectable({ providedIn: 'root' })
export class JournalVoucherExtendedResolve implements Resolve<IJournalVoucher> {
    constructor(private service: JournalVoucherExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJournalVoucher> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JournalVoucher>) => response.ok),
                map((journalVoucher: HttpResponse<JournalVoucher>) => journalVoucher.body)
            );
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
            defaultSort: 'id,asc',
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
